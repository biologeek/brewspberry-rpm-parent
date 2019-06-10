package net.brewspberry.monitoring.model;


/**
 * Identifies a model of regulation.
 * Here some examples : 
 * - ConstantRegulationModel: Temperature stays constant during a period of time
 * - LinearRegulationModel: Temperature follows a linear curve during a period of time
 * - StageRegulationModel: Temperature follows a set of ConstantRegulationModels
 *
 */
public interface RegulationModel {

}
