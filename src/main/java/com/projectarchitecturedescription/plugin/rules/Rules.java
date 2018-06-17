package com.projectarchitecturedescription.plugin.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.projectarchitecturedescription.plugin.dependencies.Dependencies;
import com.projectarchitecturedescription.plugin.dependencies.Dependency;
import com.projectarchitecturedescription.plugin.dimensions.Dimension;
import com.projectarchitecturedescription.plugin.dimensions.Dimensions;

public class Rules {
	private final Dimensions dimensions;
	private final List<Rule> rules = new ArrayList<>();
	
	@Inject
	public Rules(final Dimensions dimensions) {
		this.dimensions = dimensions;
	}
	
	public void forbidDimension(final String dim1, final String dim2) {
		this.rules.add(new ForbidRule(
				this.dimensions.getDimensionByName(dim1),
				this.dimensions.getDimensionByName(dim2)));
	}
	
	public void forbidAllDimensions() {
		this.rules.add(new ForbidAllRule(this.dimensions.getAll()));
	}
	
	public void forbidAllDimensions(final String... dimensions) {
		final List<String> dims = Arrays.asList(dimensions);
		final Set<Dimension> dimSet = dims.stream().map(this.dimensions::getDimensionByName).collect(Collectors.toSet());
		this.rules.add(new ForbidAllRule(dimSet));
	}
	
	public void allowDimensions(final String dim1, final String dim2) {
		this.rules.add(new AllowRule(this.dimensions.getDimensionByName(dim1), this.dimensions.getDimensionByName(dim2)));
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("Rules:\r\n");
		this.rules.forEach((final Rule r) -> builder.append("  " + r + "\r\n"));
		return builder.toString();
	}
	
	public void apply() {
		this.rules.forEach((final Rule r) -> r.apply());
	}
	
	public void check(final Dependencies dependencies) {
		final Set<Dependency> forbiddenDependencies = new LinkedHashSet<>();
		final Set<Dependency> allowedDependencies = new LinkedHashSet<>();
		for (final Rule r : this.rules) {
			forbiddenDependencies.addAll(r.getForbiddenDependencies().getDependencies());
			allowedDependencies.addAll(r.getAllowedDependencies().getDependencies());
		}
		forbiddenDependencies.removeAll(allowedDependencies);
		forbiddenDependencies.retainAll(dependencies.getDependencies());
		if (!forbiddenDependencies.isEmpty()) {
			throw new ForbiddenDependencyException(forbiddenDependencies);
		}
	}
}
