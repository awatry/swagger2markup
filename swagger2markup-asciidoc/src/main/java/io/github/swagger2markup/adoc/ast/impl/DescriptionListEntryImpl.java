package io.github.swagger2markup.adoc.ast.impl;

import org.asciidoctor.ast.DescriptionListEntry;
import org.asciidoctor.ast.ListItem;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.jruby.ast.impl.NodeConverter;

import java.util.List;
import java.util.Map;

public class DescriptionListEntryImpl extends StructuralNodeImpl implements DescriptionListEntry {

    private final List<ListItem> terms;

    protected DescriptionListEntryImpl(String context, Map<String, Object> attributes, List<String> roles,
                                       List<String> options, Object content, List<StructuralNode> blocks,
                                       int level, String contentModel, List<String> subs, List<ListItem> terms) {
        super(context, attributes, roles, options, content, blocks, level, contentModel, subs);
        this.terms = terms;
    }

    @Override
    public List<ListItem> getTerms() {
        return terms;
    }

    @Override
    public ListItem getDescription() {
        Object firstItem = getAt(1);
        return firstItem == null ? null : (ListItem) NodeConverter.createASTNode(firstItem);
    }

    public void setDescription(final ListItem description) {
        setAt(1, description);
    }

    private Object getAt(int i) {
        return terms.get(i);
    }

    private void setAt(int i, ListItem object) {
        terms.add(i, object);
    }


}
