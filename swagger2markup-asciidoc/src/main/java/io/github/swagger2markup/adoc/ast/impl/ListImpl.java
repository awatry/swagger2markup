package io.github.swagger2markup.adoc.ast.impl;

import org.asciidoctor.ast.List;
import org.asciidoctor.ast.StructuralNode;

import java.util.Map;

public class ListImpl extends StructuralNodeImpl implements List {


    private final java.util.List<StructuralNode> items;

    protected ListImpl(String context, Map<String, Object> attributes, java.util.List<String> roles, java.util.List<String> options, Object content, java.util.List<StructuralNode> blocks, int level, String contentModel, java.util.List<String> subs, java.util.List<StructuralNode> items) {
        super(context, attributes, roles, options, content, blocks, level, contentModel, subs);
        this.items = items;
    }

    @Override
    public java.util.List<StructuralNode> getItems() {
        return items;
    }

    @Override
    public boolean hasItems() {
        return !items.isEmpty();
    }

    @Override
    @Deprecated
    public String render() {
       return convert();
    }

    @Override
    public String convert() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

}
