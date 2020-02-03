package com.microservice.currencyexchangeservice;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mayur Gorgal
 * Creating controller for currencyExchange to accept request
 *
 */
@RestController
public class CurrencyExchangeController 
{	
	Logger log=Logger.getLogger(CurrencyExchangeController.class.getName());
	
	//representing the environment in which the current application is running
	@Autowired
	private Environment environment;
	@Autowired
	private ExchangeValueRepository exchangeValueRepository;
	
	
	/**
	 * @author Mayur Gorgal
	 * @param from
	 * @param to
	 * @return ExchangeValue
	 * 
	 * Accept the request for retriving ExchangeValue of particular currency
	 */
	@RequestMapping(value = "/currency-exchange/from/{from}/to/{to}",method = RequestMethod.GET)
	public ExchangeValue retriveExchangeValue(@PathVariable("from") String from,@PathVariable("to") String to)
	{
		
	    log.info("CurrencyExchangeController:Request for currency converion accepted from CurrencyConversionController");
	    
	    log.info("CurrencyExchangeController:finding the record using two bean values as from and to");
		ExchangeValue exchangeValue=exchangeValueRepository.findByFromAndTo(from, to);

	    log.info("CurrencyExchangeController:setting up the port number of application");
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		

	    log.info("CurrencyExchangeController:returning the response");
		return exchangeValue;
	}

}
