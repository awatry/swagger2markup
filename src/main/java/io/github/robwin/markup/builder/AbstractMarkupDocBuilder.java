/*
 *
 *  Copyright 2015 Robert Winkler
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package io.github.robwin.markup.builder;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * @author Robert Winkler
 */
public abstract class AbstractMarkupDocBuilder implements MarkupDocBuilder {

    /**
     * Explicit line break default behavior for line returns, when not specified. Please, change documentation accordingly.
     */
    private static final boolean LINE_BREAK_DEFAULT = false;

    private static final Pattern ANCHOR_UNIGNORABLE_PATTERN = Pattern.compile("[^0-9a-zA-Z-_]+");
    private static final Pattern ANCHOR_IGNORABLE_PATTERN = Pattern.compile("[\\s@#&(){}\\[\\]!$*%+=/:.;,?\\\\<>|]+");
    private static final String ANCHOR_SEPARATION_CHARACTERS = "_-";
    private static final int MAX_TITLE_LEVEL = 5;
    protected static final String NEW_LINES = "\\r\\n|\\r|\\n";
    protected static final String WHITESPACE = " ";

    protected StringBuilder documentBuilder = new StringBuilder();
    protected String newLine = System.getProperty("line.separator");
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected String anchorPrefix = null;

    @Override
    public MarkupDocBuilder withAnchorPrefix(String prefix) {
        this.anchorPrefix = prefix;
        return this;
    }

    @Override
    public String getAnchorPrefix() {
        return this.anchorPrefix;
    }

    protected void documentTitle(Markup markup, String title) {
        documentBuilder.append(markup).append(replaceNewLinesWithWhiteSpace(title)).append(newLine).append(newLine);
    }

    protected void sectionTitleWithAnchorLevel(Markup markup, int level, String title, String anchor) {
        Validate.inclusiveBetween(1, MAX_TITLE_LEVEL, level);
        documentBuilder.append(newLine);
        if (anchor != null)
            anchor(replaceNewLinesWithWhiteSpace(anchor)).newLine();
        documentBuilder.append(StringUtils.repeat(markup.toString(), level + 1)).append(" ").append(replaceNewLinesWithWhiteSpace(title)).append(newLine);
    }

    @Override
    public MarkupDocBuilder sectionTitleLevel(int level, String title) {
        return sectionTitleWithAnchorLevel(level, title, null);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel(int level, String title) {
        return sectionTitleWithAnchorLevel(level, title, null);
    }

    @Override
    public MarkupDocBuilder sectionTitleLevel1(String title) {
        return sectionTitleLevel(1, title);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel1(String title, String anchor) {
        return sectionTitleWithAnchorLevel(1, title, anchor);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel1(String title) {
        return sectionTitleWithAnchorLevel1(title, null);
    }

    @Override
    public MarkupDocBuilder sectionTitleLevel2(String title) {
        return sectionTitleLevel(2, title);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel2(String title, String anchor) {
        return sectionTitleWithAnchorLevel(2, title, anchor);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel2(String title) {
        return sectionTitleWithAnchorLevel2(title, null);
    }

    @Override
    public MarkupDocBuilder sectionTitleLevel3(String title) {
        return sectionTitleLevel(3, title);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel3(String title, String anchor) {
        return sectionTitleWithAnchorLevel(3, title, anchor);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel3(String title) {
        return sectionTitleWithAnchorLevel3(title, null);
    }

    @Override
    public MarkupDocBuilder sectionTitleLevel4(String title) {
        return sectionTitleLevel(4, title);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel4(String title, String anchor) {
        return sectionTitleWithAnchorLevel(4, title, anchor);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel4(String title) {
        return sectionTitleWithAnchorLevel4(title, null);
    }

    @Override
    public MarkupDocBuilder sectionTitleLevel5(String title) {
        return sectionTitleLevel(5, title);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel5(String title, String anchor) {
        return sectionTitleWithAnchorLevel(5, title, anchor);
    }

    @Override
    public MarkupDocBuilder sectionTitleWithAnchorLevel5(String title) {
        return sectionTitleWithAnchorLevel5(title, null);
    }

    @Override
    public MarkupDocBuilder textLine(String text, boolean forceLineBreak) {
        text(replaceNewLines(text));
        newLine(forceLineBreak);
        return this;
    }

    @Override
    public MarkupDocBuilder textLine(String text) {
        textLine(text, LINE_BREAK_DEFAULT);
        return this;
    }

    @Override
    public MarkupDocBuilder text(String text) {
        documentBuilder.append(replaceNewLines(text));
        return this;
    }

    protected void paragraph(Markup markup, String text) {
        documentBuilder.append(markup).append(newLine).append(replaceNewLines(text)).append(newLine).append(newLine);
    }

    @Override
    public MarkupDocBuilder paragraph(String text) {
        documentBuilder.append(replaceNewLines(text)).append(newLine).append(newLine);
        return this;
    }

    @Override
    public MarkupDocBuilder block(String text, MarkupBlockStyle style) {
        return block(replaceNewLines(text), style, null, null);
    }

    @Override
    public MarkupDocBuilder listing(String text) {
        return listing(replaceNewLines(text), null);
    }

    protected void delimitedBlockText(Markup markup, String text) {
        if (markup != null)
            documentBuilder.append(markup).append(newLine);
        documentBuilder.append(replaceNewLines(text)).append(newLine);
        if (markup != null)
            documentBuilder.append(markup).append(newLine);
        documentBuilder.append(newLine);
    }

    protected void delimitedTextWithoutLineBreaks(Markup markup, String text) {
        documentBuilder.append(markup).append(replaceNewLines(text)).append(markup);
    }

    protected void boldText(Markup markup, String text) {
        delimitedTextWithoutLineBreaks(markup, text);
    }

    @Override
    public MarkupDocBuilder boldTextLine(String text, boolean forceLineBreak) {
        boldText(replaceNewLines(text));
        newLine(forceLineBreak);
        return this;
    }

    @Override
    public MarkupDocBuilder boldTextLine(String text) {
        return boldTextLine(text, LINE_BREAK_DEFAULT);
    }

    protected void italicText(Markup markup, String text) {
        delimitedTextWithoutLineBreaks(markup, text);
    }

    @Override
    public MarkupDocBuilder italicTextLine(String text, boolean forceLineBreak) {
        italicText(text);
        newLine(forceLineBreak);
        return this;
    }

    @Override
    public MarkupDocBuilder italicTextLine(String text) {
        return italicTextLine(text, LINE_BREAK_DEFAULT);
    }

    protected void unorderedList(Markup markup, List<String> list) {
        documentBuilder.append(newLine);
        for (String listEntry : list) {
            unorderedListItem(markup, listEntry);
        }
        documentBuilder.append(newLine);
    }

    protected void unorderedListItem(Markup markup, String item) {
        documentBuilder.append(markup).append(item).append(newLine);
    }

    @Override
    public MarkupDocBuilder anchor(String anchor) {
        return anchor(anchor, null);
    }

    /**
     * Generic normalization algorithm for all markups (less common denominator character set).
     * Key points :
     * - Anchor is normalized (Normalized.Form.NFD)
     * - Punctuations (excluding [-_]) and spaces are replaced with escape character (depends on markup : Markup.E)
     * - Beginning, ending separation characters [-_] are ignored, repeating separation characters are simplified (keep first one)
     * - Anchor is trimmed and lower cased
     * - If the anchor still contains forbidden characters (non-ASCII, ...), replace the whole anchor with an hash (MD5).
     * - Add the anchor prefix if configured
     */
    protected String normalizeAnchor(Markup spaceEscape, String anchor) {
        String normalizedAnchor = defaultString(anchorPrefix) + anchor.trim();
        normalizedAnchor = Normalizer.normalize(normalizedAnchor, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        normalizedAnchor = ANCHOR_IGNORABLE_PATTERN.matcher(normalizedAnchor).replaceAll(spaceEscape.toString());
        normalizedAnchor = normalizedAnchor.replaceAll(String.format("([%1$s])([%1$s]+)", ANCHOR_SEPARATION_CHARACTERS), "$1");
        normalizedAnchor = StringUtils.strip(normalizedAnchor, ANCHOR_SEPARATION_CHARACTERS);
        normalizedAnchor = normalizedAnchor.trim().toLowerCase();

        String validAnchor = ANCHOR_UNIGNORABLE_PATTERN.matcher(normalizedAnchor).replaceAll("");
        if (validAnchor.length() != normalizedAnchor.length())
            normalizedAnchor = DigestUtils.md5Hex(normalizedAnchor);
        else
            normalizedAnchor = validAnchor;

        return normalizedAnchor;
    }


    @Override
    public MarkupDocBuilder crossReferenceRaw(String anchor, String text) {
        return crossReferenceRaw(null, anchor, text);
    }

    @Override
    public MarkupDocBuilder crossReferenceRaw(String anchor) {
        return crossReferenceRaw(null, anchor, null);
    }

    @Override
    public MarkupDocBuilder crossReference(String anchor, String text) {
        return crossReference(null, anchor, text);
    }

    @Override
    public MarkupDocBuilder crossReference(String anchor) {
        return crossReference(null, anchor, null);
    }

    protected void newLine(Markup markup, boolean forceLineBreak) {
        if (forceLineBreak)
            documentBuilder.append(markup);
        documentBuilder.append(newLine);
    }

    @Override
    public MarkupDocBuilder newLine() {
        newLine(LINE_BREAK_DEFAULT);
        return this;
    }

    @Override
    public MarkupDocBuilder importMarkup(Reader markupText) throws IOException {
        return importMarkup(markupText, 0);
    }

    protected void importMarkup(Markup titlePrefix, Reader markupText, int levelOffset) throws IOException {
        if (levelOffset > MAX_TITLE_LEVEL)
            throw new IllegalArgumentException(String.format("Specified levelOffset (%d) > max levelOffset (%d)", levelOffset, MAX_TITLE_LEVEL));
        if (levelOffset < -MAX_TITLE_LEVEL)
            throw new IllegalArgumentException(String.format("Specified levelOffset (%d) < min levelOffset (%d)", levelOffset, -MAX_TITLE_LEVEL));

        final Pattern titlePattern = Pattern.compile(String.format("^(%s{1,%d})\\s+(.*)$", titlePrefix, MAX_TITLE_LEVEL + 1));

        StringBuffer leveledText = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(markupText)) {
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                Matcher titleMatcher = titlePattern.matcher(readLine);

                while (titleMatcher.find()) {
                    int titleLevel = titleMatcher.group(1).length() - 1;
                    String title = titleMatcher.group(2);

                    if (titleLevel + levelOffset > MAX_TITLE_LEVEL)
                        throw new IllegalArgumentException(String.format("Specified levelOffset (%d) set title '%s' level (%d) > max title level (%d)", levelOffset, title, titleLevel, MAX_TITLE_LEVEL));
                    if (titleLevel + levelOffset < 0)
                        throw new IllegalArgumentException(String.format("Specified levelOffset (%d) set title '%s' level (%d) < 0", levelOffset, title, titleLevel));
                    else
                        titleMatcher.appendReplacement(leveledText, StringUtils.repeat(titlePrefix.toString(), 1 + titleLevel + levelOffset) + " " + title);
                }
                titleMatcher.appendTail(leveledText);
                leveledText.append(newLine);
            }
        }

        documentBuilder.append(newLine);
        documentBuilder.append(leveledText.toString());
        documentBuilder.append(newLine);
    }

    @Override
    public MarkupDocBuilder table(List<List<String>> cells) {
        return tableWithColumnSpecs(null, cells);
    }

    @Override
    public String toString() {
        return documentBuilder.toString();
    }

    protected String addFileExtension(Markup markup, String fileName) {
        return fileName + "." + markup;
    }

    @Override
    public Path addFileExtension(Path file) {
        return file.resolveSibling(addFileExtension(file.getFileName().toString()));
    }

    /**
     * 2 newLines are needed at the end of file for file to be included without protection.
     */
    @Override
    public void writeToFileWithoutExtension(Path file, Charset charset) throws IOException {
        Files.createDirectories(file.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(toString());
            writer.write(newLine);
            writer.write(newLine);
        }
        if (logger.isInfoEnabled()) {
            logger.info("Markup document written to: {}", file);
        }
    }
    
    public String replaceNewLines(String content){
        return content.replaceAll(NEW_LINES, newLine);
    }

    public String replaceNewLinesWithWhiteSpace(String content){
        return content.replaceAll(NEW_LINES, WHITESPACE);
    }

    @Override
    public void writeToFile(Path file, Charset charset) throws IOException {
        writeToFileWithoutExtension(file.resolveSibling(addFileExtension(file.getFileName().toString())), charset);
    }

    @Override
    public void writeToFileWithoutExtension(String directory, String fileName, Charset charset) throws IOException {
        writeToFileWithoutExtension(Paths.get(directory, fileName), charset);
    }

    @Override
    public void writeToFile(String directory, String fileName, Charset charset) throws IOException {
        writeToFileWithoutExtension(directory, addFileExtension(fileName), charset);
    }
}
