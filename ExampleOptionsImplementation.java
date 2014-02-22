

import java.util.ArrayList;
import java.util.List;

import org.chicago.cases.AbstractOptionsCase;
import org.chicago.cases.AbstractOptionsCase.OptionsCase;
import org.chicago.cases.options.OptionSignals.ForecastMessage;
import org.chicago.cases.options.OptionSignals.RiskMessage;
import org.chicago.cases.options.OptionSignals.VolUpdate;
import org.chicago.cases.options.OrderInfo;
import org.chicago.cases.options.OrderInfo.OrderSide;

import com.optionscity.freeway.api.IDB;
import com.optionscity.freeway.api.IJobSetup;
import math

/*
 * This is a barebones sample of a OptionCase implementation.  This sample is "working", however.
 * This means that you can launch Freeway, upload this job, and run it against the provided sample data.
 * 
 * Your team will need to provide your own implementation of this case.
 */

void checkforarb(string[] options_array){
	//Assuming we have an function for calculating implied prices
	for(int i=0,i<options_array.size(), ++i){
		double imp_price = implied_price(options_array[i]);
		if(imp_price < bid){
			//Does place_order exist? What is its type?
			order_i = placeOrder_buy(options_array[i]);
			//Will write this function later
			autohedge(order_i)
		}
		if(imp_price<ask){
			order_i = placeOrder_sell(options_array[i]);
			autohedge(order_i);
		}
	}
}

void manage_Vega(string[] orders){
	//Really hoping this is in optionsutil:
	double vega_book = calc_vega(orders);
	//We'd have a function to place the risk limits in a call/struct. 
	if(Math.abs(vega_book) >= 0.90*(Math.abs(vega_limit)){
		//We'd need to filter by moneyness and strike price. 
		//Maybe this would be easier once we see optionsutil
		string[] atm_orders = atm_filter(options_array);
		string[] june_atm_orders = date_filter(atm_orders);
		while(Math.abs(vega_book) >= 0.80*(Math.abs(vega_limit))){ 
			//I'm operating under the assumption that place_Order_buy would affect our Vega
			string order = placeOrder_buy(june_atm_orders);
			autohedge(order);
		}
	}
}

void execute

//manage_Gamma Should be almost identical to manage_Vega so let's focus on one and then copy it over


public class ExampleOptionCaseImplementation extends AbstractOptionsCase implements OptionsCase {
		
	private IDB myDatabase;
	int factor;
	private List<String> knownSymbols = new ArrayList<String>();

	public void addVariables(IJobSetup setup) {
		setup.addVariable("someFactor", "factor used to adjust something", "int", "2");
	}

	public void initializeAlgo(IDB database) {
		// Databases can be used to store data between rounds
		myDatabase = database;
		
		// helper method for accessing declared variables
		factor = getIntVar("someFactor"); 
	}

	public void newBidAsk(String idSymbol, double bid, double ask) {
		knownSymbols.add(idSymbol);
		log("I received a new bid of " + bid + ", and ask of " + ask + ", for " + idSymbol);
	}

	public void orderFilled(int volume, double fillPrice) {
		log("My order was filled with qty of " + volume + " at a price of " + fillPrice);
	}

	public void newRiskMessage(RiskMessage msg) {
		log("I received an admin message!");
	}

	public void newForecastMessage(ForecastMessage msg) {
		log("I received a forecast message!");
	}
	
	public void newVolUpdate(VolUpdate msg) {
		log("I received a vol update message!");
	}
	
	public void penaltyFill(String idSymbol, double price, int quantity) {
		log("Penalty called...oh no!");
	}

	public OrderInfo[] placeOrders() {
		// Place a buy order of 100.00 with qty of 10 for every symbol we know of
		// Note: Just a 'dummy' implementation.
		log("Placing orders");
		OrderInfo[] orders = new OrderInfo[knownSymbols.size()];
		for (int i = 0; i < knownSymbols.size(); i++) {
			String symbol = knownSymbols.get(i);
			orders[i] = new OrderInfo(symbol, OrderSide.BUY, 100.00, 10);
		}
		return orders;
	}

	public void orderFilled(String idSymbol, double price, int quantity) {
		log("My order for " + idSymbol + " got filled at " + price + " with quantity of " + quantity);
	}


	public OptionsCase getOptionCaseImplementation() {
		return this;
	}

}
