package com.xseedai.jobcreation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HiringCustomSubStageDto {

	private Long hiringCustomSubStageId;

	private String customStageName;

	private Long stageId;

	private Long jobCreationId;
	
    private boolean isSelected;

    private boolean isDefault;



}
