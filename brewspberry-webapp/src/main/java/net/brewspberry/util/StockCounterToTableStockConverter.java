package net.brewspberry.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.TableDisplayRawMaterialStockCounter;
import net.brewspberry.business.beans.TableDisplayStockCounter;
import net.brewspberry.business.beans.TableDisplayFinishedProductCounter;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;

public class StockCounterToTableStockConverter {

	/**
	 * Converts the stock counters to an easier to handle object for display
	 * 
	 * @param arg0
	 * @return
	 */
	public static TableDisplayStockCounter convert(StockCounter arg0) {
		TableDisplayStockCounter tbl = new TableDisplayStockCounter();

		double stockValue = 0;

		if (arg0 instanceof RawMaterialCounter) {
			tbl = new TableDisplayRawMaterialStockCounter((RawMaterialCounter) arg0);

			AbstractIngredient ing = (AbstractIngredient) ((TableDisplayRawMaterialStockCounter) tbl)
					.getStr_ingredient();

			stockValue = returnStockValueForAbstractIngredient(tbl, ing);

		} else if (arg0 instanceof FinishedProductCounter) {
			tbl = new TableDisplayFinishedProductCounter((FinishedProductCounter) arg0);
			AbstractFinishedProduct fp = (AbstractFinishedProduct) ((TableDisplayFinishedProductCounter) tbl)
					.getStf_product();

			stockValue = returnStockValueForAbstractFinishedProduct(tbl, fp);
		}

		tbl.setStd_stock_value(stockValue);

		return tbl;

	}

	private static double returnStockValueForAbstractIngredient(TableDisplayStockCounter tbl, AbstractIngredient ing) {
		double stockValue;

		/*
		 * Example : I have 10 kg of malt that costs 0.008 €/g
		 * 
		 * stockValue = 10 kg * 1000 g.kg^-1 * 0.008 € / 1 g.g^-1 = 80 €
		 * 
		 */
		stockValue = (tbl.getCpt_value() * tbl.getCpt_unit().getStu_multi())
				* (ing.getIng_unitary_price() / ing.getIng_unitary_price_unit().getStu_multi());
		
		return Math.round(stockValue*1000d)/1000d;
	}

	private static double returnStockValueForAbstractFinishedProduct(TableDisplayStockCounter tbl,
			AbstractFinishedProduct ing) {
		double stockValue;

		/*
		 * Example : I have 10 kg of malt that costs 0.008 €/g
		 * 
		 * stockValue = 10 kg * 1000 g.kg^-1 * 0.008 € / 1 g.g^-1 = 80 €
		 * 
		 */
		stockValue = (tbl.getCpt_value() * tbl.getCpt_unit().getStu_multi())
				* (ing.getAfp_unitary_value() / ing.getAfp_unitary_value_unit().getStu_multi());
		return stockValue;
	}

	/**
	 * Converts the list of stock counters to an easier to handle object for
	 * display
	 * 
	 * @param arg0
	 * @return
	 */
	public static List<TableDisplayStockCounter> convertList(List<StockCounter> list) {

		List<TableDisplayStockCounter> res = new ArrayList<TableDisplayStockCounter>();

		for (StockCounter stk : list) {

			res.add(convert(stk));

		}

		return res;

	}
	/**
	 * Sorts a list of stock counters and returns only clazz type counters
	 * @param counters
	 * @param clazz
	 * @return
	 */
	public static List<? extends StockCounter> sortListByType(List<? extends TableDisplayStockCounter> counters, Class<? extends StockCounter> clazz) {
		
		
		List<StockCounter> res = new ArrayList<>();
		if (counters.size() > 0) {
			for (int i = 0; i < counters.size(); i++) {
				if (counters.get(i).getClass().equals(clazz) ){
					res.add(clazz.cast(counters.get(i)));
				}
			}

		}
		return res;

	}

}
