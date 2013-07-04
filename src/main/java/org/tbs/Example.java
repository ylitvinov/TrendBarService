package org.tbs;

/**
 * @author Yury Litvinov
 */
public class Example {

    public static void main(String[] args) {
        int period = Integer.parseInt(args[0]);

        ITrendBarService service = TrendBarServiceFactory.create();

    }
}
