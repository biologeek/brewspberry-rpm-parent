package net.brewspberry.business.beans.stock;

public enum StockMotionTypeRules {

	/**
	 * Ingredients available for fabrication can either be used for fabrication
	 * or reserved for it If ingredient is broken or expired or not usable due
	 * to quality concerns, it is marked-down for quality or damage
	 */
	INGREDIENT_MOTION_DISPO_FAB(CounterTypeConstants.STOCK_DISPO_FAB, CounterTypeConstants.STOCK_RESERVE_FAB, CounterTypeConstants.STOCK_EN_FAB, CounterTypeConstants.STOCK_DEM_CASSE, CounterTypeConstants.STOCK_DEM_QUALITE, CounterTypeConstants.STOCK_DLC_DEPASSEE),
	/**
	 * When fabrication is finished for ingredient, stock must disappear.
	 */
	INGREDIENT_MOTION_EN_FAB(CounterTypeConstants.STOCK_EN_FAB, CounterTypeConstants.NONE),
	/**
	 * When producing beer, stock counter does not exist for product, it must be
	 * transferred to STOCK_BLOQUE_VENTE which refers as stock waiting to be
	 * finished or STOCK_EN_FAB if being fabricated
	 */
	PRODUCT_FROM_FAB(CounterTypeConstants.NONE, CounterTypeConstants.STOCK_BLOQUE_VENTE, CounterTypeConstants.STOCK_EN_FAB),
	/**
	 * If stock is locked for sale it can be unlocked for example at the end of
	 * lock for sale or if it does not satisfy quality requirements is marked down
	 */
	PRODUCT_FROM_BLOQUE_VENTE(CounterTypeConstants.STOCK_BLOQUE_VENTE, CounterTypeConstants.STOCK_DEM_CASSE, CounterTypeConstants.STOCK_DEM_QUALITE, CounterTypeConstants.STOCK_DISPO_VENTE, CounterTypeConstants.STOCK_DLC_DEPASSEE, CounterTypeConstants.STOCK_RESERVE_CC, CounterTypeConstants.NONE),
	/**
	 * If available for sale, can be deprecated or sold => NONE
	 */
	PRODUCT_FROM_DISPO_VENTE(CounterTypeConstants.STOCK_DISPO_VENTE, CounterTypeConstants.STOCK_DEM_CASSE, CounterTypeConstants.STOCK_DEM_QUALITE, CounterTypeConstants.STOCK_DISPO_VENTE, CounterTypeConstants.STOCK_DLC_DEPASSEE, CounterTypeConstants.STOCK_RESERVE_CC, CounterTypeConstants.NONE),
	/**
	 * If broken, stock is destroyed or drunk but not sold
	 */
	MOTION_FROM_DEM_CASSE(CounterTypeConstants.STOCK_DEM_CASSE, CounterTypeConstants.NONE),
	/**
	 * Idem
	 */
	MOTION_FROM_DEM_QUALITE(CounterTypeConstants.STOCK_DEM_QUALITE, CounterTypeConstants.NONE),
	/**
	 * Idem
	 */
	MOTION_FROM_DLC_DEPASSEE(CounterTypeConstants.STOCK_DLC_DEPASSEE, CounterTypeConstants.NONE),
	/**
	 * If reserved for customer order, when sold it disappears from stock
	 */
	MOTION_FROM_RESERVE_CC(CounterTypeConstants.STOCK_RESERVE_CC, CounterTypeConstants.NONE);

	CounterTypeConstants smt_authorized_stock_counter_from;
	CounterTypeConstants[] smt_authorized_stock_counter_to;

	private StockMotionTypeRules(CounterTypeConstants smt_authorized_stock_counter_from,
			CounterTypeConstants... smt_authorized_stock_counter_to) {
		this.smt_authorized_stock_counter_from = smt_authorized_stock_counter_from;
		this.smt_authorized_stock_counter_to = smt_authorized_stock_counter_to;
	}

	public CounterTypeConstants getSmt_authorized_stock_counter_from() {
		return smt_authorized_stock_counter_from;
	}

	public void setSmt_authorized_stock_counter_from(CounterTypeConstants smt_authorized_stock_counter_from) {
		this.smt_authorized_stock_counter_from = smt_authorized_stock_counter_from;
	}

	public CounterTypeConstants[] getSmt_authorized_stock_counter_to() {
		return smt_authorized_stock_counter_to;
	}

	public void setSmt_authorized_stock_counter_to(CounterTypeConstants[] smt_authorized_stock_counter_to) {
		this.smt_authorized_stock_counter_to = smt_authorized_stock_counter_to;
	}

}
