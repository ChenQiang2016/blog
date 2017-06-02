package com.blog.framework.mybatis;

import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class PaginationPlugin extends PluginAdapter {

	@Override
	public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
			IntrospectedTable introspectedTable) {
		interfaze.addImportedType(FullyQualifiedJavaType.getNewMapInstance());
		interfaze.addMethod(countPageByMap(method, introspectedTable));
		interfaze.addMethod(selectPageByMap(method, introspectedTable));
		return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement parentElement = document.getRootElement();

		// 在mapper.xml中添加自定义sql片段、查询片段
		addSqlWhere(parentElement, introspectedTable);
		addCountPageByMap(parentElement, introspectedTable);
		addSelectPageByMap(parentElement, introspectedTable);

		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// 在example中添加Page字段
		addPage(topLevelClass, introspectedTable, "page");
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		// 添加分页支持片段
		modifyPageMapper(element, introspectedTable);
		return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
	}

	@Override
	public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		// 添加分页支持片段
		modifyPageMapper(element, introspectedTable);
		return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
	}

	/**
	 * SelectByExample下的分页支持片段
	 * 
	 * @param element
	 * @param introspectedTable
	 */
	private void modifyPageMapper(XmlElement element, IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("if");
		isNotNullElement.addAttribute(new Attribute("test", "page != null"));
		isNotNullElement.addElement(new TextElement("limit #{page.limitStart} , #{page.pageSize}"));
		element.addElement(isNotNullElement);
	}

	public static void generate() {
		String config = PaginationPlugin.class.getClassLoader().getResource("generatorConfig.xml").getFile();
		String[] arg = { "-configfile", config, "-overwrite" };
		ShellRunner.main(arg);
	}

	@Override
	public boolean validate(List<String> arg0) {
		return true;
	}

	/**
	 * 在client中添加方法countPageByMap
	 * 
	 * @param method
	 * @param introspectedTable
	 * @return
	 */
	private Method countPageByMap(Method method, IntrospectedTable introspectedTable) {

		Method m = new Method("countPageByMap");
		m.setVisibility(method.getVisibility());
		m.setReturnType(new FullyQualifiedJavaType("long"));
		m.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String, Object>"), "map"));
		context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
		return m;
	}

	/**
	 * 在client中添加方法selectPageByMap
	 * 
	 * @param method
	 * @param introspectedTable
	 * @return
	 */
	private Method selectPageByMap(Method method, IntrospectedTable introspectedTable) {

		Method m = new Method("selectPageByMap");
		m.setVisibility(method.getVisibility());
		m.setReturnType(FullyQualifiedJavaType.getNewListInstance());
		m.addParameter(new Parameter(new FullyQualifiedJavaType("Map<String, Object>"), "map"));
		context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
		return m;
	}

	/**
	 * 在Example中添加page 属性
	 * 
	 * @param topLevelClass
	 * @param introspectedTable
	 * @param name
	 */
	private void addPage(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, String name) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.blog.framework.mybatis.Page"));
		
		CommentGenerator commentGenerator = context.getCommentGenerator();
		Field field = new Field();
		field.setVisibility(JavaVisibility.PROTECTED);
		field.setType(new FullyQualifiedJavaType(
				"com.blog.framework.mybatis.Page"));
		field.setName(name);
		commentGenerator.addFieldComment(field, introspectedTable);
		topLevelClass.addField(field);
		char c = name.charAt(0);
		String camel = Character.toUpperCase(c) + name.substring(1);
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName("set" + camel);
		method.addParameter(new Parameter(new FullyQualifiedJavaType(
				"com.blog.framework.mybatis.Page"), name));
		method.addBodyLine("this." + name + "=" + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
		method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(new FullyQualifiedJavaType(
				"com.blog.framework.mybatis.Page"));
		method.setName("get" + camel);
		method.addBodyLine("return " + name + ";");
		commentGenerator.addGeneralMethodComment(method, introspectedTable);
		topLevelClass.addMethod(method);
	}

	/**
	 * 添加按字段查询sql片段
	 * 
	 * @param parentElement
	 * @param introspectedTable
	 */
	private void addSqlWhere(XmlElement parentElement, IntrospectedTable introspectedTable) {
		// 添加sql——where
		XmlElement sql = new XmlElement("sql");
		sql.addAttribute(new Attribute("id", "sql_where"));
		StringBuilder sb = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
			XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
			sb.setLength(0);
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != null and '' != "); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
			sql.addElement(isNotNullElement);

			sb.setLength(0);
			sb.append(" and ");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = "); //$NON-NLS-1$
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
			isNotNullElement.addElement(new TextElement(sb.toString()));
		}
		parentElement.addElement(sql);
	}

	/**
	 * 在mapper.xml中创建自定义查询片段countPageByMap
	 * 
	 * @param parentElement
	 * @param introspectedTable
	 */
	public void addCountPageByMap(XmlElement parentElement, IntrospectedTable introspectedTable) {
		XmlElement select = new XmlElement("select");

		select.addAttribute(new Attribute("id", "countPageByMap"));
		select.addAttribute(new Attribute("resultType", "java.lang.Long"));
		select.addAttribute(new Attribute("parameterType", "java.util.Map"));
		select.addElement(
				new TextElement("select count(*) from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

		XmlElement where = new XmlElement("where");
		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "sql_where"));
		where.addElement(include);

		select.addElement(where);

		parentElement.addElement(select);
	}

	/**
	 * 在mapper.xml中创建自定义查询片段selectPageByMap
	 * 
	 * @param parentElement
	 * @param introspectedTable
	 */
	public void addSelectPageByMap(XmlElement parentElement, IntrospectedTable introspectedTable) {
		XmlElement select = new XmlElement("select");
		select.addAttribute(new Attribute("id", "selectPageByMap"));
		select.addAttribute(new Attribute("resultMap", "BaseResultMap"));
		select.addAttribute(new Attribute("parameterType", "java.util.Map"));
		select.addElement(new TextElement(" select"));
		XmlElement include_column = new XmlElement("include");
		include_column.addAttribute(new Attribute("refid", "Base_Column_List"));
		select.addElement(include_column);

		select.addElement(new TextElement(" from " + introspectedTable.getFullyQualifiedTableNameAtRuntime()));

		XmlElement where = new XmlElement("where");

		XmlElement include = new XmlElement("include");
		include.addAttribute(new Attribute("refid", "sql_where"));
		where.addElement(include);

		select.addElement(where);

		XmlElement if_page = new XmlElement("if");
		if_page.addAttribute(new Attribute("test", "limitStart != null and pageSize > 0"));
		if_page.addElement(new TextElement("limit #{limitStart} , #{pageSize}"));

		select.addElement(if_page);

		parentElement.addElement(select);
	}
}