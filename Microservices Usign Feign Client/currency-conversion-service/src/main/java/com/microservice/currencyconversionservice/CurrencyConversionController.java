package com.microservice.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



/**
 * @author Mayur Gorgal 
 * All currency conversion request accept here.
 */
@RestController
public class CurrencyConversionController 
{
	
	Logger log=Logger.getLogger(CurrencyConversionController.class.getName());

	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	@Autowired
	private Environment environment;
	
	
	/**
	 * @param from
	 * @param to
	 * @param quantity
	 * @return CurrencyConversionBean
	 * 
	 * Example of REST Template to call microservices
	 */
	@RequestMapping(value = "/currency-conversion/from/{from}/to/{to}/quantity/{quantity}",method = RequestMethod.GET)
	public CurrencyConversionBean convertCurrency(@PathVariable("from") String from, @PathVariable("to") String to,
			@PathVariable("quantity") BigDecimal quantity) {

		log.info("CurrencyConversionBean:GET Request for currency converion are mapped");
		
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		// creating RestTemplate class for calling other rest api from spring
		RestTemplate restTemplate = new RestTemplate();
		// calling get method to get the response from currency exchange service
		// adding url, then in bean you want to convert data,value of the pathvariables as from and to
		ResponseEntity<CurrencyConversionBean> currencyConversionBeanResponseEntity = restTemplate.getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);

		log.info("CurrencyConversionBean: Response from currency-exchange service gets accepted inside ResponseEntity");
		// getting the body of the ResponseEntity class into CurrencyConversionBean
		CurrencyConversionBean currencyConversionBeanResponseBean = currencyConversionBeanResponseEntity.getBody();

		// perform calculation of total amount using value of currecny that we want to
		// convert
		log.info("CurrencyConversionBean: performing calculation perform to convert one currency into another");
		currencyConversionBeanResponseBean.setTotalCalculatedAmount(
				currencyConversionBeanResponseBean.getConversionMultiple().multiply(quantity));

		// setting the number of quantity that need to convert
		currencyConversionBeanResponseBean.setQuantity(quantity);
		currencyConversionBeanResponseBean.setPort(Integer.parseInt(environment.getProperty("local.server.port")));

		// returning the bean
		log.info("CurrencyConversionBean:returning the response to the user");
		return currencyConversionBeanResponseBean;
	}
	
	/**
	 * @author Mayur Gorgal
	 * @param from
	 * @param to
	 * @param quantity
	 * @return CurrencyConversionBean
	 * Example of Currency Conversion Feign Client to call microservices
	 */
	@RequestMapping(value ="/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}" ,method = RequestMethod.GET)
	public CurrencyConversionBean convertCurrencyUsingFeignClient(@PathVariable("from") String from,@PathVariable("to") String to,@PathVariable("quantity") BigDecimal quantity)
	{
		log.info("CurrencyConversionController:Request mapped inside convertCurrencyUsingFeignClient");
		log.info("CurrencyConversionController:calling FeignClient of CurrencyExchangeService");
		CurrencyConversionBean conversionBean=proxy.convertCurrency(from, to);
		
		
		conversionBean.setTotalCalculatedAmount(
				conversionBean.getConversionMultiple().multiply(quantity));
		conversionBean.setQuantity(quantity);
		
		return conversionBean;
	}
}
