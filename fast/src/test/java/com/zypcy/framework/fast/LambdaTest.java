package com.zypcy.framework.fast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testHashMap(){
        HashMap<String , String> userMap = new HashMap<>();
        userMap.put("name" , "zhuyu");
        userMap.put("name" , "zhangsan");
        System.out.println(userMap.get("name"));

        userMap.remove("name");

        System.out.println(userMap.get("name"));
    }

    /**
     * 把List中的项生成字符串，以逗号拼接
     */
    @Test
    public void testJoinDouhao(){
        List<String> roles = new ArrayList<>();
        roles.add("1");
        roles.add("2");
        roles.add("3");
        roles.add("4");

        String result= roles.stream().collect(Collectors.joining(","));
        System.out.println(result);
    }

    @Test
    public void testLambda(){
        List<Transaction> transactions = initData();
        //1.找出2011年发生的所有交易，并按交易额排序，由低到高
        //List<Transaction> transactions1 = transactions.stream().filter( t -> t.getYear() == 2011).sorted(Comparator.comparing(Transaction :: getValue)).collect(Collectors.toList());
        //System.out.println("transactions1:"+transactions1.toString());

        //List<String> transactions2 = transactions.stream().map( t -> t.getTrader().getCity()).distinct().collect(Collectors.toList());
        //System.out.println("transactions2:"+transactions2.toString());

        //List<String> transactions3 = transactions.stream().filter( t-> t.getTrader().getCity().equals("cambridge")).map( t-> t.getTrader().getName() ).distinct().sorted().collect(Collectors.toList());
        //System.out.println("transactions3:"+transactions3.toString());

        //boolean transactions5 = transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("milan"));
        //System.out.println("transactions5:"+ transactions5);

        //transactions.stream().filter( t-> t.getTrader().getCity().equals("cambridge")).map(t -> t.getValue()).forEach(t -> System.out.println(t));

        //int max7 = transactions.stream().map(t -> t.getValue()).reduce(0 , Integer::max);
        //System.out.println("max7:"+max7);

        //Optional<Transaction> transaction8 = transactions.stream().min(Comparator.comparing(Transaction::getValue));
        //System.out.println("transaction8:"+transaction8.get().toString());
    }

    private List<Transaction> initData(){
        Trader raoul = new Trader("Raoul" , "cambridge");
        Trader mario = new Trader("Mario" , "milan");
        Trader alan = new Trader("Alan" , "cambridge");
        Trader brian = new Trader("Brian" , "cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian , 2011 , 300),
                new Transaction(raoul , 2012 , 1000),
                new Transaction(raoul , 2011 , 400),
                new Transaction(mario , 2012 , 710),
                new Transaction(mario , 2012 , 700),
                new Transaction(alan , 2012 , 950)
        );
        return transactions;
    }

    public class Trader {
        private String name;
        private String city;
        public Trader(){}
        public Trader(String name, String city) {
            this.name = name;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Trader{" +
                    "name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    '}';
        }
    }

    public class Transaction{
        private Trader trader;
        private int year;
        private int value;
        public Transaction(){}
        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return trader;
        }

        public void setTrader(Trader trader) {
            this.trader = trader;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Transaction{" +
                    "trader=" + trader +
                    ", year=" + year +
                    ", value=" + value +
                    '}';
        }
    }
}
