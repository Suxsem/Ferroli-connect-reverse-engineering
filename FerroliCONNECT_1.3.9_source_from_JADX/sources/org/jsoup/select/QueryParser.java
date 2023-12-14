package org.jsoup.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.parser.TokenQueue;
import org.jsoup.select.CombiningEvaluator;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.jsoup.select.StructuralEvaluator;

public class QueryParser {
    private static final String[] AttributeEvals = {"=", "!=", "^=", "$=", "*=", "~="};
    private static final Pattern NTH_AB = Pattern.compile("((\\+|-)?(\\d+)?)n(\\s*(\\+|-)?\\s*\\d+)?", 2);
    private static final Pattern NTH_B = Pattern.compile("(\\+|-)?(\\d+)");
    private static final String[] combinators = {",", ">", MqttTopic.SINGLE_LEVEL_WILDCARD, "~", " "};
    private List<Evaluator> evals = new ArrayList();
    private String query;

    /* renamed from: tq */
    private TokenQueue f4138tq;

    private QueryParser(String str) {
        this.query = str;
        this.f4138tq = new TokenQueue(str);
    }

    public static Evaluator parse(String str) {
        return new QueryParser(str).parse();
    }

    /* access modifiers changed from: package-private */
    public Evaluator parse() {
        this.f4138tq.consumeWhitespace();
        if (this.f4138tq.matchesAny(combinators)) {
            this.evals.add(new StructuralEvaluator.Root());
            combinator(this.f4138tq.consume());
        } else {
            findElements();
        }
        while (!this.f4138tq.isEmpty()) {
            boolean consumeWhitespace = this.f4138tq.consumeWhitespace();
            if (this.f4138tq.matchesAny(combinators)) {
                combinator(this.f4138tq.consume());
            } else if (consumeWhitespace) {
                combinator(' ');
            } else {
                findElements();
            }
        }
        if (this.evals.size() == 1) {
            return this.evals.get(0);
        }
        return new CombiningEvaluator.And((Collection<Evaluator>) this.evals);
    }

    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void combinator(char r11) {
        /*
            r10 = this;
            org.jsoup.parser.TokenQueue r0 = r10.f4138tq
            r0.consumeWhitespace()
            java.lang.String r0 = r10.consumeSubQuery()
            org.jsoup.select.Evaluator r0 = parse(r0)
            java.util.List<org.jsoup.select.Evaluator> r1 = r10.evals
            int r1 = r1.size()
            r2 = 44
            r3 = 1
            r4 = 0
            if (r1 != r3) goto L_0x0033
            java.util.List<org.jsoup.select.Evaluator> r1 = r10.evals
            java.lang.Object r1 = r1.get(r4)
            org.jsoup.select.Evaluator r1 = (org.jsoup.select.Evaluator) r1
            boolean r5 = r1 instanceof org.jsoup.select.CombiningEvaluator.C2592Or
            if (r5 == 0) goto L_0x003a
            if (r11 == r2) goto L_0x003a
            r5 = r1
            org.jsoup.select.CombiningEvaluator$Or r5 = (org.jsoup.select.CombiningEvaluator.C2592Or) r5
            org.jsoup.select.Evaluator r5 = r5.rightMostEvaluator()
            r6 = 1
            r9 = r5
            r5 = r1
            r1 = r9
            goto L_0x003c
        L_0x0033:
            org.jsoup.select.CombiningEvaluator$And r1 = new org.jsoup.select.CombiningEvaluator$And
            java.util.List<org.jsoup.select.Evaluator> r5 = r10.evals
            r1.<init>((java.util.Collection<org.jsoup.select.Evaluator>) r5)
        L_0x003a:
            r5 = r1
            r6 = 0
        L_0x003c:
            java.util.List<org.jsoup.select.Evaluator> r7 = r10.evals
            r7.clear()
            r7 = 62
            r8 = 2
            if (r11 != r7) goto L_0x0057
            org.jsoup.select.CombiningEvaluator$And r11 = new org.jsoup.select.CombiningEvaluator$And
            org.jsoup.select.Evaluator[] r2 = new org.jsoup.select.Evaluator[r8]
            r2[r4] = r0
            org.jsoup.select.StructuralEvaluator$ImmediateParent r0 = new org.jsoup.select.StructuralEvaluator$ImmediateParent
            r0.<init>(r1)
            r2[r3] = r0
            r11.<init>((org.jsoup.select.Evaluator[]) r2)
            goto L_0x00ae
        L_0x0057:
            r7 = 32
            if (r11 != r7) goto L_0x006c
            org.jsoup.select.CombiningEvaluator$And r11 = new org.jsoup.select.CombiningEvaluator$And
            org.jsoup.select.Evaluator[] r2 = new org.jsoup.select.Evaluator[r8]
            r2[r4] = r0
            org.jsoup.select.StructuralEvaluator$Parent r0 = new org.jsoup.select.StructuralEvaluator$Parent
            r0.<init>(r1)
            r2[r3] = r0
            r11.<init>((org.jsoup.select.Evaluator[]) r2)
            goto L_0x00ae
        L_0x006c:
            r7 = 43
            if (r11 != r7) goto L_0x0081
            org.jsoup.select.CombiningEvaluator$And r11 = new org.jsoup.select.CombiningEvaluator$And
            org.jsoup.select.Evaluator[] r2 = new org.jsoup.select.Evaluator[r8]
            r2[r4] = r0
            org.jsoup.select.StructuralEvaluator$ImmediatePreviousSibling r0 = new org.jsoup.select.StructuralEvaluator$ImmediatePreviousSibling
            r0.<init>(r1)
            r2[r3] = r0
            r11.<init>((org.jsoup.select.Evaluator[]) r2)
            goto L_0x00ae
        L_0x0081:
            r7 = 126(0x7e, float:1.77E-43)
            if (r11 != r7) goto L_0x0096
            org.jsoup.select.CombiningEvaluator$And r11 = new org.jsoup.select.CombiningEvaluator$And
            org.jsoup.select.Evaluator[] r2 = new org.jsoup.select.Evaluator[r8]
            r2[r4] = r0
            org.jsoup.select.StructuralEvaluator$PreviousSibling r0 = new org.jsoup.select.StructuralEvaluator$PreviousSibling
            r0.<init>(r1)
            r2[r3] = r0
            r11.<init>((org.jsoup.select.Evaluator[]) r2)
            goto L_0x00ae
        L_0x0096:
            if (r11 != r2) goto L_0x00bd
            boolean r11 = r1 instanceof org.jsoup.select.CombiningEvaluator.C2592Or
            if (r11 == 0) goto L_0x00a3
            org.jsoup.select.CombiningEvaluator$Or r1 = (org.jsoup.select.CombiningEvaluator.C2592Or) r1
            r1.add(r0)
            r11 = r1
            goto L_0x00ae
        L_0x00a3:
            org.jsoup.select.CombiningEvaluator$Or r11 = new org.jsoup.select.CombiningEvaluator$Or
            r11.<init>()
            r11.add(r1)
            r11.add(r0)
        L_0x00ae:
            if (r6 == 0) goto L_0x00b7
            r0 = r5
            org.jsoup.select.CombiningEvaluator$Or r0 = (org.jsoup.select.CombiningEvaluator.C2592Or) r0
            r0.replaceRightMostEvaluator(r11)
            r11 = r5
        L_0x00b7:
            java.util.List<org.jsoup.select.Evaluator> r0 = r10.evals
            r0.add(r11)
            return
        L_0x00bd:
            org.jsoup.select.Selector$SelectorParseException r0 = new org.jsoup.select.Selector$SelectorParseException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unknown combinator: "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            java.lang.Object[] r1 = new java.lang.Object[r4]
            r0.<init>(r11, r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.select.QueryParser.combinator(char):void");
    }

    private String consumeSubQuery() {
        StringBuilder sb = new StringBuilder();
        while (!this.f4138tq.isEmpty()) {
            if (this.f4138tq.matches("(")) {
                sb.append("(");
                sb.append(this.f4138tq.chompBalanced('(', ')'));
                sb.append(")");
            } else if (this.f4138tq.matches("[")) {
                sb.append("[");
                sb.append(this.f4138tq.chompBalanced('[', ']'));
                sb.append("]");
            } else if (this.f4138tq.matchesAny(combinators)) {
                break;
            } else {
                sb.append(this.f4138tq.consume());
            }
        }
        return sb.toString();
    }

    private void findElements() {
        if (this.f4138tq.matchChomp(MqttTopic.MULTI_LEVEL_WILDCARD)) {
            byId();
        } else if (this.f4138tq.matchChomp(".")) {
            byClass();
        } else if (this.f4138tq.matchesWord() || this.f4138tq.matches("*|")) {
            byTag();
        } else if (this.f4138tq.matches("[")) {
            byAttribute();
        } else if (this.f4138tq.matchChomp(Constraint.ANY_ROLE)) {
            allElements();
        } else if (this.f4138tq.matchChomp(":lt(")) {
            indexLessThan();
        } else if (this.f4138tq.matchChomp(":gt(")) {
            indexGreaterThan();
        } else if (this.f4138tq.matchChomp(":eq(")) {
            indexEquals();
        } else if (this.f4138tq.matches(":has(")) {
            has();
        } else if (this.f4138tq.matches(":contains(")) {
            contains(false);
        } else if (this.f4138tq.matches(":containsOwn(")) {
            contains(true);
        } else if (this.f4138tq.matches(":containsData(")) {
            containsData();
        } else if (this.f4138tq.matches(":matches(")) {
            matches(false);
        } else if (this.f4138tq.matches(":matchesOwn(")) {
            matches(true);
        } else if (this.f4138tq.matches(":not(")) {
            not();
        } else if (this.f4138tq.matchChomp(":nth-child(")) {
            cssNthChild(false, false);
        } else if (this.f4138tq.matchChomp(":nth-last-child(")) {
            cssNthChild(true, false);
        } else if (this.f4138tq.matchChomp(":nth-of-type(")) {
            cssNthChild(false, true);
        } else if (this.f4138tq.matchChomp(":nth-last-of-type(")) {
            cssNthChild(true, true);
        } else if (this.f4138tq.matchChomp(":first-child")) {
            this.evals.add(new Evaluator.IsFirstChild());
        } else if (this.f4138tq.matchChomp(":last-child")) {
            this.evals.add(new Evaluator.IsLastChild());
        } else if (this.f4138tq.matchChomp(":first-of-type")) {
            this.evals.add(new Evaluator.IsFirstOfType());
        } else if (this.f4138tq.matchChomp(":last-of-type")) {
            this.evals.add(new Evaluator.IsLastOfType());
        } else if (this.f4138tq.matchChomp(":only-child")) {
            this.evals.add(new Evaluator.IsOnlyChild());
        } else if (this.f4138tq.matchChomp(":only-of-type")) {
            this.evals.add(new Evaluator.IsOnlyOfType());
        } else if (this.f4138tq.matchChomp(":empty")) {
            this.evals.add(new Evaluator.IsEmpty());
        } else if (this.f4138tq.matchChomp(":root")) {
            this.evals.add(new Evaluator.IsRoot());
        } else {
            throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.f4138tq.remainder());
        }
    }

    private void byId() {
        String consumeCssIdentifier = this.f4138tq.consumeCssIdentifier();
        Validate.notEmpty(consumeCssIdentifier);
        this.evals.add(new Evaluator.C2593Id(consumeCssIdentifier));
    }

    private void byClass() {
        String consumeCssIdentifier = this.f4138tq.consumeCssIdentifier();
        Validate.notEmpty(consumeCssIdentifier);
        this.evals.add(new Evaluator.Class(consumeCssIdentifier.trim()));
    }

    private void byTag() {
        String consumeElementSelector = this.f4138tq.consumeElementSelector();
        Validate.notEmpty(consumeElementSelector);
        if (consumeElementSelector.startsWith("*|")) {
            this.evals.add(new CombiningEvaluator.C2592Or(new Evaluator.Tag(consumeElementSelector.trim().toLowerCase()), new Evaluator.TagEndsWith(consumeElementSelector.replace("*|", ":").trim().toLowerCase())));
            return;
        }
        if (consumeElementSelector.contains("|")) {
            consumeElementSelector = consumeElementSelector.replace("|", ":");
        }
        this.evals.add(new Evaluator.Tag(consumeElementSelector.trim()));
    }

    private void byAttribute() {
        TokenQueue tokenQueue = new TokenQueue(this.f4138tq.chompBalanced('[', ']'));
        String consumeToAny = tokenQueue.consumeToAny(AttributeEvals);
        Validate.notEmpty(consumeToAny);
        tokenQueue.consumeWhitespace();
        if (tokenQueue.isEmpty()) {
            if (consumeToAny.startsWith("^")) {
                this.evals.add(new Evaluator.AttributeStarting(consumeToAny.substring(1)));
            } else {
                this.evals.add(new Evaluator.Attribute(consumeToAny));
            }
        } else if (tokenQueue.matchChomp("=")) {
            this.evals.add(new Evaluator.AttributeWithValue(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("!=")) {
            this.evals.add(new Evaluator.AttributeWithValueNot(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("^=")) {
            this.evals.add(new Evaluator.AttributeWithValueStarting(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("$=")) {
            this.evals.add(new Evaluator.AttributeWithValueEnding(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("*=")) {
            this.evals.add(new Evaluator.AttributeWithValueContaining(consumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("~=")) {
            this.evals.add(new Evaluator.AttributeWithValueMatching(consumeToAny, Pattern.compile(tokenQueue.remainder())));
        } else {
            throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", this.query, tokenQueue.remainder());
        }
    }

    private void allElements() {
        this.evals.add(new Evaluator.AllElements());
    }

    private void indexLessThan() {
        this.evals.add(new Evaluator.IndexLessThan(consumeIndex()));
    }

    private void indexGreaterThan() {
        this.evals.add(new Evaluator.IndexGreaterThan(consumeIndex()));
    }

    private void indexEquals() {
        this.evals.add(new Evaluator.IndexEquals(consumeIndex()));
    }

    private void cssNthChild(boolean z, boolean z2) {
        String lowerCase = this.f4138tq.chompTo(")").trim().toLowerCase();
        Matcher matcher = NTH_AB.matcher(lowerCase);
        Matcher matcher2 = NTH_B.matcher(lowerCase);
        int i = 2;
        int i2 = 0;
        if ("odd".equals(lowerCase)) {
            i2 = 1;
        } else if (!"even".equals(lowerCase)) {
            if (matcher.matches()) {
                int parseInt = matcher.group(3) != null ? Integer.parseInt(matcher.group(1).replaceFirst("^\\+", "")) : 1;
                if (matcher.group(4) != null) {
                    i2 = Integer.parseInt(matcher.group(4).replaceFirst("^\\+", ""));
                }
                i = parseInt;
            } else if (matcher2.matches()) {
                i2 = Integer.parseInt(matcher2.group().replaceFirst("^\\+", ""));
                i = 0;
            } else {
                throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", lowerCase);
            }
        }
        if (z2) {
            if (z) {
                this.evals.add(new Evaluator.IsNthLastOfType(i, i2));
            } else {
                this.evals.add(new Evaluator.IsNthOfType(i, i2));
            }
        } else if (z) {
            this.evals.add(new Evaluator.IsNthLastChild(i, i2));
        } else {
            this.evals.add(new Evaluator.IsNthChild(i, i2));
        }
    }

    private int consumeIndex() {
        String trim = this.f4138tq.chompTo(")").trim();
        Validate.isTrue(StringUtil.isNumeric(trim), "Index must be numeric");
        return Integer.parseInt(trim);
    }

    private void has() {
        this.f4138tq.consume(":has");
        String chompBalanced = this.f4138tq.chompBalanced('(', ')');
        Validate.notEmpty(chompBalanced, ":has(el) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Has(parse(chompBalanced)));
    }

    private void contains(boolean z) {
        this.f4138tq.consume(z ? ":containsOwn" : ":contains");
        String unescape = TokenQueue.unescape(this.f4138tq.chompBalanced('(', ')'));
        Validate.notEmpty(unescape, ":contains(text) query must not be empty");
        if (z) {
            this.evals.add(new Evaluator.ContainsOwnText(unescape));
        } else {
            this.evals.add(new Evaluator.ContainsText(unescape));
        }
    }

    private void containsData() {
        this.f4138tq.consume(":containsData");
        String unescape = TokenQueue.unescape(this.f4138tq.chompBalanced('(', ')'));
        Validate.notEmpty(unescape, ":containsData(text) query must not be empty");
        this.evals.add(new Evaluator.ContainsData(unescape));
    }

    private void matches(boolean z) {
        this.f4138tq.consume(z ? ":matchesOwn" : ":matches");
        String chompBalanced = this.f4138tq.chompBalanced('(', ')');
        Validate.notEmpty(chompBalanced, ":matches(regex) query must not be empty");
        if (z) {
            this.evals.add(new Evaluator.MatchesOwn(Pattern.compile(chompBalanced)));
        } else {
            this.evals.add(new Evaluator.Matches(Pattern.compile(chompBalanced)));
        }
    }

    private void not() {
        this.f4138tq.consume(":not");
        String chompBalanced = this.f4138tq.chompBalanced('(', ')');
        Validate.notEmpty(chompBalanced, ":not(selector) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Not(parse(chompBalanced)));
    }
}
