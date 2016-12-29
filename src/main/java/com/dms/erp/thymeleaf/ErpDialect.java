package com.dms.erp.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.dms.erp.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.dms.erp.thymeleaf.processor.MessageElementTagProcessor;
import com.dms.erp.thymeleaf.processor.OrderElementTagProcessor;
import com.dms.erp.thymeleaf.processor.PaginatorElementTagProcessor;


public class ErpDialect extends AbstractProcessorDialect {

	public ErpDialect() {
		super("ERP system", "erp", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginatorElementTagProcessor(dialectPrefix));
		return processadores;
	}

}
