package com.microservice.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author Nabla
 * Creating feign client to call the method present inside currency Exchange Service
 */
@FeignClient(name ="currency-exchange-service" ,url = "localhost:8000")
public interface CurrencyExchangeServiceProxy 
{

	
	/**
	 * @author Mayur Gorgal
	 * @param from
	 * @param to
	 * @return CurrencyConversionBean
	 * Created proxy method to call Get method of currency exchange service
	 */
	@RequestMapping(value = "/currency-exchange/from/{from}/to/{to}",method = RequestMethod.GET)
	public CurrencyConversionBean convertCurrency(@PathVariable("from") String from,@PathVariable("to") String to);
	
}
