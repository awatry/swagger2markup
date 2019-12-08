package io.github.swagger2markup.adoc.ast.impl;

import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.ast.Title;
import org.jruby.Ruby;

import java.util.List;
import java.util.Map;

public class DocumentImpl extends StructuralNodeImpl implements Document {

    private final Map<Object, Object> docOptions;

    protected DocumentImpl(String context, Map<String, Object> attributes, List<String> roles, List<String> options,
                           Object content, List<StructuralNode> blocks, int level, String contentModel, List<String> subs, Map<Object, Object> docOptions) {
        super(context, attributes, roles, options, content, blocks, level, contentModel, subs);
        this.docOptions = docOptions;
    }

    @Override
    public boolean isBasebackend(String backend) {
        return isAttribute("basebackend", backend);
    }

    @Override
    @Deprecated
    public boolean basebackend(String backend) {
        return isBasebackend(backend);
    }

    @Override
    public Map<Object, Object> getOptions() {
        return docOptions;
    }

    @Override
    public Title getStructuredDoctitle() {
        return (Title) docOptions.get("doctitle");
    }

    @Override
    public String getDoctitle() {
        return getStructuredDoctitle().getCombined();
    }

    @Override
    @Deprecated
    public String doctitle() {
        return getDoctitle();
    }

    @Override
    public int getAndIncrementCounter(String name) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public int getAndIncrementCounter(String name, int initialValue) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public boolean isSourcemap() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }

    @Override
    public void setSourcemap(boolean state) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }
}
