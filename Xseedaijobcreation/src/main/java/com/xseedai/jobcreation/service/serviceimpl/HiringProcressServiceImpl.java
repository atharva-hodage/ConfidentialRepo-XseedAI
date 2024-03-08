package com.xseedai.jobcreation.service.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xseedai.jobcreation.dto.HiringCustomSubStageDto;
import com.xseedai.jobcreation.entity.HiringCustomSubStage;
import com.xseedai.jobcreation.entity.HiringDefaultSubStage;
import com.xseedai.jobcreation.entity.HiringStage;
import com.xseedai.jobcreation.entity.JobCreation;
import com.xseedai.jobcreation.repository.HiringCustomSubStageRepository;
import com.xseedai.jobcreation.repository.HiringDefaultSubStageRepository;
import com.xseedai.jobcreation.repository.HiringStageRepository;
import com.xseedai.jobcreation.repository.JobCreationRepository;
import com.xseedai.jobcreation.service.HiringProcressService;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class HiringProcressServiceImpl implements HiringProcressService {

	@Autowired
	private HiringStageRepository hiringStageRepository;

	@Autowired
	private HiringDefaultSubStageRepository hiringDefaultSubStageRepository;

	@Autowired
	private HiringCustomSubStageRepository hiringCustomSubStageRepository;
	
	@Autowired
	private JobCreationRepository jobCreationRepository;
	
    @Autowired
    private ModelMapper modelMapper;
    
	public HiringStage getHiringStageById(Long hiringStageId) {
		Optional<HiringStage> hiringStageOptional = hiringStageRepository.findById(hiringStageId);
		return hiringStageOptional.orElse(null);
	}

	@Override
	public HiringStage addHiringStage(HiringStage hiringStage) {
		return hiringStageRepository.save(hiringStage);
	}

	@Override
	public HiringDefaultSubStage addHiringDefaultSubStage(HiringDefaultSubStage hiringDefaultSubStage,
			Long hiringStageId) {
		HiringStage hiringStage = getHiringStageById(hiringStageId);
		hiringDefaultSubStage.setStage(hiringStage);
		return hiringDefaultSubStageRepository.save(hiringDefaultSubStage);
	}

//	@Override
//	public HiringCustomSubStage addHiringCustomSubStage(HiringCustomSubStageDto hiringCustomSubStageDto, Long hiringStageId) {
//		HiringCustomSubStage hiringCustomSubStage = new HiringCustomSubStage();
//		
//		 JobCreation jobCreation = jobCreationRepository.findById(hiringCustomSubStageDto.getJobCreationId())
//	                .orElseThrow(() -> new IllegalArgumentException("JobCreation not found with ID: " + hiringCustomSubStageDto.getJobCreationId()));
//	
//		hiringCustomSubStage.setCustomStageName(hiringCustomSubStageDto.getCustomStageName());
//		hiringCustomSubStage.setDefault(false);
//		hiringCustomSubStage.setJobCreation(jobCreation);
//		hiringCustomSubStage.setSelected(false);
//		
//		
//		HiringStage hiringStage = getHiringStageById(hiringStageId);
//		hiringCustomSubStage.setStage(hiringStage);
//		return hiringCustomSubStageRepository.save(hiringCustomSubStage);
//
//	}
	
	
	@Override
	public HiringCustomSubStage addHiringCustomSubStage(String customStageName, Long jobCreationId, Long hiringStageId) {
	    HiringCustomSubStage hiringCustomSubStage = new HiringCustomSubStage();

	    JobCreation jobCreation = jobCreationRepository.findById(jobCreationId)
	            .orElseThrow(() -> new IllegalArgumentException("JobCreation not found with ID: " + jobCreationId));

	    hiringCustomSubStage.setCustomStageName(customStageName);
	    hiringCustomSubStage.setDefault(false);
	    hiringCustomSubStage.setJobCreation(jobCreation);
	    hiringCustomSubStage.setSelected(false);

	    HiringStage hiringStage = getHiringStageById(hiringStageId);
	    hiringCustomSubStage.setStage(hiringStage);
	    return hiringCustomSubStageRepository.save(hiringCustomSubStage);
	}


	@Override
	public List<HiringCustomSubStageDto> getCustomSubStagesByStageAndJob(Long stageId, Long jobCreationId) {
        List<HiringCustomSubStage> subStages = hiringCustomSubStageRepository.findByStage_HiringStageIdAndJobCreation_JobId(stageId, jobCreationId);
        return subStages.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private HiringCustomSubStageDto convertToDto(HiringCustomSubStage subStage) {
        HiringCustomSubStageDto dto = new HiringCustomSubStageDto();
        dto.setHiringCustomSubStageId(subStage.getHiringCustomSubStageId());
        dto.setCustomStageName(subStage.getCustomStageName());
        dto.setStageId(subStage.getStage().getHiringStageId());
        dto.setJobCreationId(subStage.getJobCreation().getJobId());
        dto.setSelected(subStage.isSelected());
        dto.setDefault(subStage.isDefault());
        return dto;
    }

//    @Override
////    @Transactional
//    public void deselectSubStages(List<Long> subStageIds) {
//        List<HiringCustomSubStage> subStages = hiringCustomSubStageRepository.findAllById(subStageIds);
//        subStages.forEach(subStage -> subStage.setSelected(false)); // Deselect all substages
//        hiringCustomSubStageRepository.saveAll(subStages);
//    }

 

    @Override
    public List<HiringCustomSubStageDto> switchToDefault(Long jobId) {
        // Delete entries where isDefault is false for the given jobId
        hiringCustomSubStageRepository.deleteByJobCreation_JobIdAndIsDefaultFalse(jobId);
        // Fetch entries where isDefault is true for the given jobId
        List<HiringCustomSubStage> defaultStages = hiringCustomSubStageRepository
                .findByJobCreation_JobIdAndIsDefaultTrue(jobId);
        return defaultStages.stream().map(this::convertToDto).collect(Collectors.toList());
    }

	@Override
    public ResponseEntity<String> updateSubStageSelection(List<Long> subStageIds) {
        List<HiringCustomSubStage> subStages = hiringCustomSubStageRepository.findAllById(subStageIds);
        if (subStages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sub stages found with provided IDs.");
        }

        subStages.forEach(subStage -> subStage.setSelected(!subStage.isSelected()));
        hiringCustomSubStageRepository.saveAll(subStages);

        return ResponseEntity.status(HttpStatus.OK).body("Sub stages selection updated successfully.");
    }
 

}
