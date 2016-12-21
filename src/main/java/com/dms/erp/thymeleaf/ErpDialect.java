package com.dms.erp.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.dms.erp.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import com.dms.erp.thymeleaf.processor.MessageElementTagProcessor;


public class ErpDialect extends AbstractProcessorDialect {

	public ErpDialect() {
		super("ERP system", "erp", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		return processadores;
	}

}
