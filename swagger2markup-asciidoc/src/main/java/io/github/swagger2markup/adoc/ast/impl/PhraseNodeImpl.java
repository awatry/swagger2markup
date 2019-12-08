package io.github.swagger2markup.adoc.ast.impl;

import org.asciidoctor.ast.PhraseNode;

import java.util.List;
import java.util.Map;

public class PhraseNodeImpl extends ContentNodeImpl implements PhraseNode {

    private final String type;
    private final String text;
    private final String target;

    protected PhraseNodeImpl(String context, Map<String, Object> attributes, List<String> roles, List<String> options, String type, String text, String target) {
        super(context, attributes, roles, options);
        this.type = type;
        this.text = text;
        this.target = target;
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

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getTarget() {
        return target;
    }
}
