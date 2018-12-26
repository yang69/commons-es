package com.github.yang69.commons.es;

import java.util.ArrayList;
import java.util.List;

/**
 * @author          |   |   |
 *               ,   .-'"'=;_  ,
 *               |\.'-~`-.`-`;/|
 *               \.` '.'~-.` './
 *               (\`,__=-'__,'/)
 *            _.-'-.( d\_/b ).-'-._
 *          /'.-'   ' .---. '   '-.`\
 *        /'  .' (=    (_)    =) '.  `\
 *       /'  .',  `-.__.-.__.-'  ,'.  `\
 *      (     .'.   V       V  ; '.     )
 *      (    |::  `-,__.-.__,-'  ::|    )
 *      |   /|`:.               .:'|\   |
 *      |  / | `:.              :' |`\  |
 *      | |  (  :.             .:  )  | |
 *      | |   ( `:.            :' )   | |
 *      | |    \ :.           .: /    | |
 *      | |     \`:.         .:'/     | |
 *      ) (      `\`:.     .:'/'      ) (
 *      (  `)_     ) `:._.:' (     _(`  )
 *      \  ' _)  .'           `.  (_ `  /
 *       \  '_) /   .'"```"'.   \ (_`  /
 *        `'"`  \  (         )  /  `"'`
 *    ___        `.`.       .'.'        ___
 *  .`   ``"""'''--`_)     (_'--'''"""``   `.
 * (_(_(___...--'"'`         `'"'--...___)_)_)
 *
 * @version 1.0
 * Created by Yang on 2018-12-12.
 *
 * 构造 Elasticsearch DSL 查询语句的工具类。
 * Utils to generate DSL query string.
 *
 * 用法：
 * usage:
 *      String dslString = new ElasticsearchQuery()
 *          .filter("name", "yang")
 *          .notFilter("sex", "male")
 *          .range("age", "gte", 18, "lt", 60)
 *          .size(10)
 *          .from(1)
 *          .build()
 *      该语句查询满足条件
 *      （ name 字段为 “yang"） 且 （sex 字段 不是 "male"） 且 （age 字段大于等于18小于60）
 *      的结果，忽略最开始的1个结果，取接下来的10条结果。
 */
public class ElasticsearchQuery {

    private List<Object[]> filters;

    private List<Object[]> notFilters;

    private int size;

    private int from;

    public ElasticsearchQuery() {
        this.filters = new ArrayList<Object[]>();
        this.notFilters = new ArrayList<Object[]>();
        this.size = 10;
        this.from = 0;
    }

    /**
     * 分页查询的页面大小
     * @param size 页面大小
     */
    public ElasticsearchQuery size(int size) {
        this.size = size;
        return this;
    }

    /**
     * 分页查询的起始点（从0开始计数）
     * @param from 忽略的最开始的记录数
     */
    public ElasticsearchQuery from(int from) {
        this.from = from;
        return this;
    }

    /**
     * 选出满足条件的记录
     * @param fieldName 待查询的字段名
     * @param value 需满足的值
     */
    public ElasticsearchQuery filter(String fieldName, Object value) {
        this.filters.add(new Object[]{TermEnum.Term, fieldName, value});
        return this;
    }

    public ElasticsearchQuery orFilter(String fieldName, Iterable<?> values) {
        this.filters.add(new Object[]{TermEnum.Terms, fieldName, values});
        return this;
    }

    public ElasticsearchQuery orFilter(String fieldName, Object[] values) {
        this.filters.add(new Object[]{TermEnum.Terms, fieldName, values});
        return this;
    }

    public ElasticsearchQuery notAny(String fieldName, Iterable<?> values) {
        this.notFilters.add(new Object[]{TermEnum.Terms, fieldName, values});
        return this;
    }

    public ElasticsearchQuery notAny(String fieldName, Object[] values) {
        this.notFilters.add(new Object[]{TermEnum.Terms, fieldName, values});
        return this;
    }

    public ElasticsearchQuery notFilter(String fieldName, Object value) {
        this.notFilters.add(new Object[]{TermEnum.Term, fieldName, value});
        return this;
    }

    public ElasticsearchQuery exists(String fieldName) {
        this.filters.add(new Object[]{TermEnum.Exists, fieldName});
        return this;
    }

    public ElasticsearchQuery notExists(String fieldName) {
        this.notFilters.add(new Object[]{TermEnum.Exists, fieldName});
        return this;
    }

    public ElasticsearchQuery range(String fieldName, Object... params) {
        if (params == null || params.length == 0) {
            return this;
        }
        if ((params.length & 1) != 0) {
            throw new IllegalArgumentException("mismatch params for range(), must be in pair");
        }
        this.filters.add(new Object[]{TermEnum.Range, fieldName, params});
        return this;
    }

    public ElasticsearchQuery notInRange(String fieldName, Object... params) {
        if (params == null || params.length == 0) {
            return this;
        }
        if ((params.length & 1) != 0) {
            throw new IllegalArgumentException("mismatch params for notInRange(), must be in pair");
        }
        this.notFilters.add(new Object[]{TermEnum.Range, fieldName, params});
        return this;
    }

    /**
     * 生成DSL语句
     * generate DSL string
     * @return DSL语句 | DSL string
     */
    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"query\":");
        if (isEmptyQuery()) {
            sb.append("{\"match_all\":{}}");
        } else {
            sb.append("{\"bool\":{\"filter\":{\"bool\":{");
            if (!isEmptyList(filters)) {
                buildBoolHelper(sb, "must", filters);
                if (!isEmptyList(notFilters)) {
                    sb.append(',');
                    buildBoolHelper(sb, "must_not", notFilters);
                }
            } else {
                buildBoolHelper(sb, "must_not", notFilters);
            }

            sb.append("}}}}");
        }
        sb.append(",\"size\":").append(size);
        sb.append(",\"from\":").append(from);
        sb.append("}");
        return sb.toString();
    }

    private void buildBoolHelper(StringBuilder sb, String boolType, List<Object[]> filters) {
        if (!isEmptyList(filters)) {
            sb.append("\"").append(boolType).append("\":[");
            for (Object[] array : filters) {
                switch ((TermEnum) array[0]) {
                    case Term:
                        sb.append("{\"term\":{\"")
                                .append(array[1]).append("\":");
                        if (array[2] instanceof String) {
                            sb.append("\"").append(array[2]).append("\"");
                        } else {
                            sb.append(array[2]);
                        }
                        sb.append("}},");
                        break;
                    case Exists:
                        sb.append("{\"exists\":{\"field\":\"")
                                .append(array[1]).append("\"}},");
                        break;
                    case Range:
                        sb.append("{\"range\":{\"").append(array[1]).append("\":{");
                        Object[] params = (Object[]) array[2];
                        int length = params.length;
                        if ((length & 1) != 0) {
                            throw new IllegalArgumentException("number of params should be even for range() and notInRange()");
                        }
                        for (int i = 0; i < length; i++) {
                            Object paramName = params[i];
                            Object paramValue = params[++i];
                            writeString(sb, (String) paramName);
                            sb.append(':');
                            if (paramValue instanceof String) {
                                writeString(sb, (String) paramValue);
                            } else {
                                sb.append(paramValue);
                            }
                            sb.append(',');
                        }
                        sb.deleteCharAt(sb.length() - 1).append("}}},");
                        break;
                    case Terms:
                        sb.append("{\"terms\":{\"").append(array[1]).append("\":[");
                        Object values = array[2];
                        if (values instanceof Object[]) {
                            for (Object value : (Object[]) values) {
                                writeValue(sb, value);
                                sb.append(',');
                            }
                        } else if (values instanceof Iterable<?>) {
                            for (Object value : (Iterable<?>) values) {
                                writeValue(sb, value);
                                sb.append(',');
                            }
                        }
                        sb.deleteCharAt(sb.length() - 1).append("]}},");
                        break;
                    default:
                }
            }
            sb.deleteCharAt(sb.length() - 1).append("]");
        }
    }

    private void writeValue(StringBuilder sb, Object value) {
        if (value instanceof String) {
            writeString(sb, (String) value);
        } else {
            sb.append(value);
        }
    }

    private void writeString(StringBuilder sb, String string) {
        sb.append('"').append(string).append('"');
    }

    private boolean isEmptyQuery() {
        return isEmptyList(filters)
                && isEmptyList(notFilters);
    }

    private boolean isEmptyList(List<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 异星镇体，bug无形
     * with aliens cute, no bugs occur
     *
     *                           XXXXXXXXXXXXXXXXXXXXXXX
     *                      XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
     *                   XXXX                                XXXX
     *               XXXX                                        XXXX
     *            XXX                                                XXX
     *          XX                                                      XX
     *        XX                                                          XX
     *       XX                                                            XX
     *      XX                                                              XX
     *     XX                                                        X       XX
     *    XX                                                   XX     XX      XX
     *   XX                                                      XXX    XX     XX
     *  XX      XX   XX                                             XX         XX
     *  XX    XX   XX                                                 XX        XX
     * XX    X    X                                                    XX       XX
     * XX   X    X                                                               X
     * X   X    X                                                                X
     * X       X              8                                 8                X
     * X                       8                               8                 X
     * X                  8     8                             8   8              X
     * X                   8  8  8                           8  8   8            X
     * X                    8  8  8                         8  8  88             X
     * X                     8  8  8                       XXXX  8               X
     * X                      8 XXXX                       XXXXX8                X
     * XX                      XXXXXX                    XXXXXXXX               XX
     * XX                     XXXXXXXX                  XXXXXXXXXX              XX
     * XX                    XXXXXXXXXX                XXXXXXXXXXXX             XX
     *  XX                  XXXXXXXXXXXX               XXXXXXXXXXXXX           XX
     *   XX                 XXXXXXXXXXXXX             XXXXXXXXXXXXXX          XX
     *   XX                XXXXXXXXXXXXXX            XXXXXXXXXXXXXXX          XX
     *   XX                XXXXXXXXXXXXXX           XXXXXXXXXXXXXXXX          XX
     *    XX              XXXXXXXXXXXXXXX           XXXXXXXXXXXXXXXX         XX
     *     XX             XXXXXXXXXXXXXXX           XXXXXXX    XXXXX        XX
     *      XX            XXXXXXX   XXXXX           XXXXXX      XXXX       XX
     *      XX            XXXXXX     XXX            XXXXX       XXXX       XX
     *       XX           XXXXX  88  XXXX           XXXX   88   XXX       XX
     *       XX           XXXX  8888  XX            XXXX  8888  XXX       XX
     *        XX          XXXX  8888 XXX            XXXX  8888 XXX       XX
     *         XX         XXXXX  88 XXX              XXXX  88 XXX       XX
     *           XX        XXXX    XXX               XXXX    XXX       XX
     *            XXX       XXXXXXXXX                 XXXXXXXXX       XXX
     *            XX          XXXXX      XXXXXXXXXXX    XXXXX           XX
     *           XXX           XX    XXXX           XXX  XX             XXX
     *           XX                XX XXXXX          XXXXX                XX
     *           XX               X  XX    XXXX  XXXX  XXXX   XXXX        XX
     *           XX                    XXX     XX     XX   XXX    X       XX
     *           XX                       XXX     XXX                    XX
     *            XX                         XXXXX                     XXX
     *              XX                                               XXX
     *               XXXXX                                        XXXX
     *                    XXXXXXXXXXX                     XXXXXXXX
     *                               XXXX             XXXX
     *                                  XX           XX
     *                                   XX         XX
     *                                   XX         XX
     *                                   XX         XX
     *                                    XX       XX
     *                                     XX      XX
     *                                     XX      XX
     *                                  XXXXX       XXXX
     *                                XX                XX
     *                              XX X XX        XX X XX
     *                             XX  XX             XX XX
     *                            XX  XX               XX XX
     *                           XX   XX               XX  XX
     *                          XX   XX                 XX  XX
     *                        XX    XX                   XX  XX
     *                       XX    XX                    XX   XX
     *                      XX    XX                     XX   XX
     *                     XX    XX                       XX   XX
     *                     XX    XX                       XX   XX
     *                     XX  XX                         XX   XX
     *                     XX  XX                         XX   XX
     *                      XX XX                         XX  XX
     *                       XXXX                         XXXX
     *                         XX                         XX
     *                         XX                         XX
     *                         XX                         XX
     *                         XX                         XX
     *                         XX                        XX
     *                         XX                       XX
     *                          XX                     XX
     *                          XX                     XX
     *                           XX                   XX
     *                            XX                 XX
     *                            XX     XXXXX     XX
     *                             XX  XX     XX  X
     *                              X  X       X  X
     *          XXXXXXX             X  X       X  X
     *    XXXXXX       XXXX         X  X       X  X           XXXXXX
     *  XXX                XXXXX    X  X       X  X      XXXXX      XXXXXX
     * XX     XXXX              XXXXX  X       X  X  XXXX                 XXX
     * X    XX  XX                     X     XXX  XXX              XXX      XX
     * X   X  XX                       XX   XX                      XXXX     XX
     * X  X XX                         XX   XX                        XXXX    XX
     * X X X                        XXXX     XXXX                       X XX   X
     * XX  X                    XXXX             XXX                     X X   X
     * XX X                  XXX                    XXX                   X X  X
     * XX XX               XX                         XXX                 X X  X
     *      X           XXX                              XX               X X X
     *       XXXXXXXXXXX                                   XXXX          XXXXX
     *                                                         XXXXXXXXXX
     */

}
