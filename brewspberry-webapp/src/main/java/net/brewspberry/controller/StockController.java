package net.brewspberry.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.service.StockServiceImpl;
import net.brewspberry.util.StockCounterToTableStockConverter;

@Controller
@WebServlet("/stock.do")
@Transactional
public class StockController extends AbstractAutowiredHttpServlet {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private static final long serialVersionUID = 2916648763166648082L;

	@Autowired
	private IGenericService<StockCounter> stockCounterService;


	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String option = request.getParameter("o");

		if (option == null || option.equals("")) {
			option = "dispstk";
		}

		switch (option) {

		case "dispstk":

			/*
			 * Displaying whole stock
			 */

			List<StockCounter> stockCounters = stockCounterService
					.getAllElements();

			if (stockCounters.size() > 0) {

				request.setAttribute("counters",
						StockCounterToTableStockConverter
								.convertList(stockCounters));

				request.setAttribute("counters",
						StockCounterToTableStockConverter
								.convertList(stockCounters));

			}

			request.getRequestDispatcher("dispstk.jsp").forward(request,
					response);
			break;

		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
