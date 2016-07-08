package net.brewspberry.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.stock.StockCounter;

@Controller
@WebServlet("/stock.do")
public class StockController extends HttpServlet {

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private static final long serialVersionUID = 2916648763166648082L;

	
	
	
	private IGenericService<StockCounter> stockCounterService;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		String option = request.getParameter("o");
		
		
		if (option == null || option.equals("")){
			option = "dispstk";			
		}
		
		switch (option){
		
		case "dispstk" :
			
			/*
			 * Displaying whole stock
			 */
			
			List<StockCounter> stockCounters = this.stockCounterService.getAllElements();
			
			if (stockCounters.size()>0){
				
							
				request.setAttribute("counters", stockCounters);
				
			}
			
			request.getRequestDispatcher("dispstk.jsp").forward(request, response);
		break;
		
		}
		
		
	}
	
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	
}
