package com.microservice.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Mayur Gorgal
 * 
 * Access ExchangeValue data from database
 *
 */
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue,Long>
{
	
	/**
	 * @author Mayur Gorgal
	 * @param from
	 * @param to
	 * @return ExchangeValue
	 * find the record using fromCurrency and toCurrency
	 */
	ExchangeValue findByFromAndTo(String from,String to);

}
