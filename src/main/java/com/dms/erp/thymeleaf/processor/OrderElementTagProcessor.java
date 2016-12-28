package com.dms.erp.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Create element tag, defined internally by the attribute {@code TAG_NAME}
 * 
 * @author Normandes
 * @author Diorgenes Morais
 * @version 1.0.1
 */
public class OrderElementTagProcessor extends AbstractElementTagProcessor {

	/*
	 * Define tag name
	 */
	private static final String TAG_NAME = "order";
	private static final int PRECEDENCE = 1000;

	public OrderElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {

		IModelFactory modelFactory = context.getModelFactory();
		/*
		 * Get attribute of view, to get the values passed by the attributes of
		 * the tag
		 */
		IAttribute page = tag.getAttribute("page");
		IAttribute field = tag.getAttribute("field");
		/*
		 * Caso seja necessário parssar um nome com espaçamento passar entre
		 * aspas simples ('Nome da tag').
		 */
		IAttribute text = tag.getAttribute("text");

		IModel model = modelFactory.createModel();
		/*
		 * Add fragment of code (localization: resources/templates)
		 */
		model.add(modelFactory.createStandaloneElementTag("th:block", "th:replace",
				String.format("layout/fragments/Ordination :: order (%s, %s, %s)", page.getValue(), field.getValue(),
						text.getValue())));

		structureHandler.replaceWith(model, true);

	}

}
