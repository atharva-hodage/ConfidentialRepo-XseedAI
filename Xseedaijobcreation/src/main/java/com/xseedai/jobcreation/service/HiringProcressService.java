package com.xseedai.jobcreation.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.xseedai.jobcreation.dto.HiringCustomSubStageDto;
import com.xseedai.jobcreation.entity.HiringCustomSubStage;
import com.xseedai.jobcreation.entity.HiringDefaultSubStage;
import com.xseedai.jobcreation.entity.HiringStage;

public interface HiringProcressService {

	HiringStage addHiringStage(HiringStage hiringStage);

	HiringDefaultSubStage addHiringDefaultSubStage(HiringDefaultSubStage hiringDefaultSubStage,Long hiringStageId);
	
	HiringCustomSubStage addHiringCustomSubStage(String customStageName, Long jobCreationId, Long hiringStageId);	
    List<HiringCustomSubStageDto> getCustomSubStagesByStageAndJob(Long stageId, Long jobCreationId);

//    void deselectSubStages(List<Long> subStageIds);

    List<HiringCustomSubStageDto> switchToDefault(Long jobId);
    
    ResponseEntity<String> updateSubStageSelection(List<Long> subStageIds);




}
