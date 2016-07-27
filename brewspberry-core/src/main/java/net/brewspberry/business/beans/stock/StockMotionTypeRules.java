package net.brewspberry.business.beans.stock;

public enum StockMotionTypeRules {

	/**
	 * Ingredients available for fabrication can either be used for fabrication
	 * or reserved for it If ingredient is broken or expired or not usable due
	 * to quality concerns, it is marked-down for quality or damage
	 */
	INGREDIENT_MOTION_DISPO_FAB(CounterType.STOCK_DISPO_FAB, CounterType.STOCK_RESERVE_FAB, CounterType.STOCK_EN_FAB, CounterType.STOCK_DEM_CASSE, CounterType.STOCK_DEM_QUALITE, CounterType.STOCK_DLC_DEPASSEE),
	/**
	 * When fabrication is finished for ingredient, stock must disappear.
	 */
	INGREDIENT_MOTION_EN_FAB(CounterType.STOCK_EN_FAB, CounterType.NONE),
	/**
	 * When producing beer, stock counter does not exist for product, it must be
	 * transferred to STOCK_BLOQUE_VENTE which refers as stock waiting to be
	 * finished or STOCK_EN_FAB if being fabricated
	 */
	PRODUCT_FROM_FAB(CounterType.NONE, CounterType.STOCK_BLOQUE_VENTE, CounterType.STOCK_EN_FAB),
	/**
	 * If stock is locked for sale it can be unlocked for example at the end of
	 * lock for sale or if it does not satisfy quality requirements is marked down
	 */
	PRODUCT_FROM_BLOQUE_VENTE(CounterType.STOCK_BLOQUE_VENTE, CounterType.STOCK_DEM_CASSE, CounterType.STOCK_DEM_QUALITE, CounterType.STOCK_DISPO_VENTE, CounterType.STOCK_DLC_DEPASSEE, CounterType.STOCK_RESERVE_CC, CounterType.NONE),
	/**
	 * If available for sale, can be deprecated or sold => NONE
	 */
	PRODUCT_FROM_DISPO_VENTE(CounterType.STOCK_DISPO_VENTE, CounterType.STOCK_DEM_CASSE, CounterType.STOCK_DEM_QUALITE, CounterType.STOCK_DISPO_VENTE, CounterType.STOCK_DLC_DEPASSEE, CounterType.STOCK_RESERVE_CC, CounterType.NONE),
	/**
	 * If broken, stock is destroyed or drunk but not sold
	 */
	MOTION_FROM_DEM_CASSE(CounterType.STOCK_DEM_CASSE, CounterType.NONE),
	/**
	 * Idem
	 */
	MOTION_FROM_DEM_QUALITE(CounterType.STOCK_DEM_QUALITE, CounterType.NONE),
	/**
	 * Idem
	 */
	MOTION_FROM_DLC_DEPASSEE(CounterType.STOCK_DLC_DEPASSEE, CounterType.NONE),
	/**
	 * If reserved for customer order, when sold it disappears from stock
	 */
	MOTION_FROM_RESERVE_CC(CounterType.STOCK_RESERVE_CC, CounterType.NONE);

	CounterType smt_authorized_stock_counter_from;
	CounterType[] smt_authorized_stock_counter_to;

	private StockMotionTypeRules(CounterType smt_authorized_stock_counter_from,
			CounterType... smt_authorized_stock_counter_to) {
		this.smt_authorized_stock_counter_from = smt_authorized_stock_counter_from;
		this.smt_authorized_stock_counter_to = smt_authorized_stock_counter_to;
	}

	public CounterType getSmt_authorized_stock_counter_from() {
		return smt_authorized_stock_counter_from;
	}

	public void setSmt_authorized_stock_counter_from(CounterType smt_authorized_stock_counter_from) {
		this.smt_authorized_stock_counter_from = smt_authorized_stock_counter_from;
	}

	public CounterType[] getSmt_authorized_stock_counter_to() {
		return smt_authorized_stock_counter_to;
	}

	public void setSmt_authorized_stock_counter_to(CounterType[] smt_authorized_stock_counter_to) {
		this.smt_authorized_stock_counter_to = smt_authorized_stock_counter_to;
	}

}
