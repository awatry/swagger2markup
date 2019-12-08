package io.github.swagger2markup.adoc.ast.impl;

import org.asciidoctor.ast.Section;
import org.asciidoctor.ast.StructuralNode;

import java.util.List;
import java.util.Map;

public class SectionImpl extends StructuralNodeImpl implements Section {

    private int index;
    private int number;
    private String numeral;
    private String sectionName;
    private boolean special;
    private boolean numbered;

    protected SectionImpl(String context, Map<String, Object> attributes, List<String> roles, List<String> options, Object content, List<StructuralNode> blocks, int level, String contentModel, List<String> subs) {
        super(context, attributes, roles, options, content, blocks, level, contentModel, subs);
    }

    @Override
    @Deprecated
    public int index() {
        return getIndex();
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    @Deprecated
    public int number() {
        return getNumber();
    }

    @Override
    @Deprecated
    public int getNumber() {
        return number;
    }

    @Override
    public String getNumeral() {
        return numeral;
    }

    @Override
    @Deprecated
    public String sectname() {
        return getSectionName();
    }

    @Override
    public String getSectionName() {
        return sectionName;
    }

    @Override
    @Deprecated
    public boolean special() {
        return isSpecial();
    }

    @Override
    public boolean isSpecial() {
        return special;
    }

    @Override
    @Deprecated
    public boolean numbered() {
        return isNumbered();
    }

    @Override
    public boolean isNumbered() {
        return numbered;
    }

}
