package com.algaworks.alganews.metrics.api.assembler;

import com.algaworks.alganews.metrics.api.model.EditorMonthlyEarningsModel;
import com.algaworks.alganews.metrics.api.view.EditorMonthlyEarningsProjection;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EditorMonthlyEarningsModelAssembler {
	
	private final ModelMapper modelMapper;
	
	public EditorMonthlyEarningsModel toModel(EditorMonthlyEarningsProjection earning) {
		return modelMapper.map(earning, EditorMonthlyEarningsModel.class);
	}
	
	public List<EditorMonthlyEarningsModel> toModelCollection(List<EditorMonthlyEarningsProjection> earnings) {
		return earnings.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
