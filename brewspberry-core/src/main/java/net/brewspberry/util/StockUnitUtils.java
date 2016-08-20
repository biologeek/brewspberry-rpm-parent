package net.brewspberry.util;

import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.exceptions.UnitConversionFailedException;

public class StockUnitUtils {

	/**
	 * Converts value to its standard parent unit (gram, litre, meter, ...)
	 * 
	 * @param value
	 *            value to convert
	 * @param unitFrom
	 *            unit from which is converted value
	 * @return converted value. To get unit of this value, see
	 *         {@link #getStandardUnitFromNonStandardUnit(StockUnit)}
	 */
	public static double convertToStandardUnit(double value, StockUnit unitFrom) {

		StockUnit unit = unitFrom;
		double currentValue = value;
		while (unit.hasParent()) {

			currentValue = currentValue * unit.getStu_multi();
			unit = unit.getStu_parent();

		}

		return currentValue;

	}

	/**
	 * Returns parent unit of param unitFrom. Unit returned is a standard (gram,
	 * litre, meter, ...)
	 * 
	 * @param unitFrom
	 *            non standard unit
	 * @return standard parent unit
	 */
	public static StockUnit getStandardUnitFromNonStandardUnit(
			StockUnit unitFrom) {

		StockUnit unit = unitFrom;
		while (unit.getStu_parent() != null) {

			for (StockUnit parentUnit : StockUnit.values()) {
				if (unit.getStu_parent() != null) {
					if (unit.getStu_parent().equals(parentUnit)) {
						unit = parentUnit;
					}
				}
			}

		}

		return unit;

	}

	/**
	 * 
	 * @param value
	 * @param unitFrom
	 * @param unitTo
	 * @return
	 * @throws UnitConversionFailedException
	 */
	public static double convertToOtherUnit(double value, StockUnit unitFrom,
			StockUnit unitTo) throws UnitConversionFailedException {

		StockUnit currentUnit = unitFrom;
		double currentValue = value;

		if (unitFrom.equals(unitTo))
			return value;

		// 1- Bottom - Up

		while (currentUnit.hasParent()) {

			currentValue = currentValue * currentUnit.getStu_multi();
			if (currentUnit.getStu_parent() != null) {
				if (currentUnit.getStu_parent().equals(unitTo)) {

					return currentValue;

				} else {
					currentUnit = currentUnit.getStu_parent();
				}
			}

		}

		// 2- Top - Down

		int loopCounter = 0;
		do {

			for (StockUnit unit : StockUnit.values()) {
				if (unit.getStu_parent() != null) {
					if (unit.getStu_parent().equals(currentUnit)) {

						if (unit.equals(unitTo)) {
							currentValue = currentValue / unit.getStu_multi();
						
							return currentValue;
						} 
					}
				}
				loopCounter ++;
			}

		} while (!currentUnit.equals(unitTo) && loopCounter <= StockUnit.values().length);

		throw new UnitConversionFailedException("Impossible to convert "
				+ unitFrom.getStu_value() + " to " + unitTo.getStu_value());

	}

}
