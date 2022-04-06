package com.fordevs.processor;

import com.fordevs.mysql.entity.OutputStudent;
import com.fordevs.postgresql.entity.InputStudent;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessor implements org.springframework.batch.item.ItemProcessor<InputStudent, OutputStudent> {

	@Override
	public OutputStudent process(InputStudent item) throws Exception {
		
		System.out.println(item.getId());
		
		OutputStudent outputStudent = new
				OutputStudent();
		
		outputStudent.setId(item.getId());
		outputStudent.setFirstName(item.getFirstName());
		outputStudent.setLastName(item.getLastName());
		outputStudent.setEmail(item.getEmail());
		outputStudent.setDeptId(item.getDeptId());
		outputStudent.setIsActive(item.getIsActive() != null ? Boolean.valueOf(item.getIsActive()) : false );
		
		return outputStudent;
		
	}

}
