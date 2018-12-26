package com.github.yang69.commons.es;

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
 * @version 2018-12-15
 * Created by Yang on 2018-12-15.
 */
public enum TermEnum {

    /**
     * term 语句
     */
    Term("term"),
    /**
     * terms 语句
     */
    Terms("terms"),
    /**
     * range 语句
     */
    Range("range"),
    /**
     * exists 语句
     */
    Exists("exists");

    private String name;

    private TermEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
