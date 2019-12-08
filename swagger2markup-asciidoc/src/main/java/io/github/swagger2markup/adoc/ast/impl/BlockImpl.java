package io.github.swagger2markup.adoc.ast.impl;

import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.StructuralNode;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BlockImpl extends StructuralNodeImpl implements Block {

    private List<String> lines;

    protected BlockImpl(String context, Map<String, Object> attributes, List<String> roles, List<String> options, Object content, List<StructuralNode> blocks, int level, String contentModel, List<String> subs) {
        super(context, attributes, roles, options, content, blocks, level, contentModel, subs);
    }

    @Override
    @Deprecated
    public List<String> lines() {
        return getLines();
    }

    @Override
    public List<String> getLines() {
        return lines;
    }

    @Override
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    @Deprecated
    public String source() {
        return getSource();
    }

    @Override
    public String getSource() {
        return String.join("\n", lines);
    }

    @Override
    public void setSource(String source) {
        setLines(Arrays.asList(source.split("\n")));
    }
}
