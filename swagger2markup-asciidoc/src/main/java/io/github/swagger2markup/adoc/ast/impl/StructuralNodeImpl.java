package io.github.swagger2markup.adoc.ast.impl;

import org.asciidoctor.ast.Cursor;
import org.asciidoctor.ast.StructuralNode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StructuralNodeImpl extends ContentNodeImpl implements StructuralNode {

    private String title;
    private String caption;
    private String style;
    private final Object content;
    private final List<StructuralNode> blocks;
    private final int level;
    private final String contentModel;
    private List<String> subs;

    protected StructuralNodeImpl(String context, Map<String, Object> attributes, List<String> roles, List<String> options,
                                 Object content, List<StructuralNode> blocks, int level, String contentModel, List<String> subs) {
        super(context, attributes, roles, options);
        this.content = content;
        this.blocks = blocks;
        this.level = level;
        this.contentModel = contentModel;
        this.subs = subs;
    }

    @Override
    @Deprecated
    public String title() {
        return getTitle();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    @Deprecated
    public String style() {
        return getStyle();
    }

    @Override
    public String getStyle() {
        return style;
    }

    @Override
    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    @Deprecated
    public List<StructuralNode> blocks() {
        return getBlocks();
    }

    @Override
    public List<StructuralNode> getBlocks() {
        return blocks;
    }

    @Override
    public void append(StructuralNode block) {
    }

    @Override
    @Deprecated
    public Object content() {
        return getContent();
    }

    @Override
    public Object getContent() {
        return content;
    }

    @Override
    public String convert() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public Cursor getSourceLocation() {
        return new CursorImpl();
    }

    @Override
    public String getContentModel() {
        return contentModel;
    }

    @Override
    public List<String> getSubstitutions() {
        return subs;
    }

    @Override
    public boolean isSubstitutionEnabled(String substitution) {
        return subs.contains(substitution);
    }

    @Override
    public void removeSubstitution(String substitution) {
        subs.remove(substitution);
    }

    @Override
    public void addSubstitution(String substitution) {
        subs.add(substitution);
    }

    @Override
    public void prependSubstitution(String substitution) {
    }

    @Override
    public void setSubstitutions(String... substitutions) {
        subs = Arrays.asList(substitutions);
    }

    @Override
    public List<StructuralNode> findBy(Map<Object, Object> selector) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

}
