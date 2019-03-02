package com.github.yang69.commons.es;

/**
 * @version 2019-03-02
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
 * Created by Yang on 2018-12-16.
 */
public class ElasticsearchQueryTest {
    public static void main(String[] args) {
        // match all query
        System.out.println("match all query -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .build()
        );
        System.out.println();

        // match all query with page info
        System.out.println("match all query -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .size(1)
                        .from(1)
                        .build()
        );
        System.out.println();

        // exists query
        System.out.println("exists query -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .exists("phone")
                        .build()
        );
        System.out.println();

        // not exists query
        System.out.println("not exists query -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .notExists("phone")
                        .build()
        );
        System.out.println();

        // filter query
        System.out.println("filter query -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .satisfy("name", "yang")
                        .exists("phone").build()
        );
        System.out.println();

        // range query
        System.out.println("range query -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .range("age", "gt", 20, "lte", 21)
                        .build()
        );
        System.out.println();

        // not in range query
        System.out.println("not in range query -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .notInRange("age", "gt", 23)
                        .build()
        );
        System.out.println();

        // filter query with not
        System.out.println("filter query with not -->");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .satisfy("name", "yang")
                        .exclude("hobbies", "computer")
                        .build()
        );
        System.out.println();

        // test something more complex
        System.out.println("test something more complex");
        System.out.println(
                new ElasticsearchQuery.Builder()
                        .satisfy("name", "yang")
                        .satisfy("hobbies", "c")
                        .satisfyAny("hobbies", new Object[]{"fiction", "book"})
                        .satisfyAny("bookPrices", new Object[]{20, 35, 41})
                        .excludeAll("hobbies", new String[]{"tv"})
                        .range("age", "gte", 18, "lt", 60)
                        .exists("money")
                        .notExists("phone")
                        .notInRange("age", "gt", 15)
                        .size(1)
                        .from(2)
                        .build()
        );
    }
}
