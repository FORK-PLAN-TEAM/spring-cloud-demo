package com.zypcy.framework.fast;

import com.alibaba.fastjson.JSON;
import com.zypcy.framework.fast.bus.dto.CashbookSaveDto;
import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import com.zypcy.framework.fast.bus.service.ICashbookService;
import com.zypcy.framework.fast.bus.service.ICashbookStatisticsService;
import com.zypcy.framework.fast.sys.service.IZySysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {

    @Test
    public void contextLoads() {
    }

    /*
    测试ModelMapper映射
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testModelMapper(){
        CashbookSaveDto saveDto = new CashbookSaveDto();
        saveDto.setAmount(20.00);
        saveDto.setCashCategory("12");
        saveDto.setCashId("a");
        saveDto.setCashType("b");
        saveDto.setIsdel(false);
        saveDto.setRecordTime(new Date());
        saveDto.setRemark("remark");

        Cashbook cashbook = modelMapper.map(saveDto , Cashbook.class);
        System.out.println(JSON.toJSON(cashbook));
    }*/

    /*@Autowired
    private IZySysUserService userService;
    @Autowired
    private ICashbookService cashbookService;
    @Autowired
    private ICashbookStatisticsService statisticsService;

    @Test
    public void testInit(){
        List<String> days = new ArrayList<>();
        for(int i=7; i<= 9 ;i++){
            for(int j=1 ; j <= 31 ; j++){
                String day = "20190"+ i;
                if(j < 10){
                    day = day + "0" + j;
                }else{
                    day = day + j;
                }
                days.add(day);
            }
        }
        for(int j=9 ; j <= 21 ; j++){
            String day = "201910";
            if(j < 10){
                day = day + "0" + j;
            }else{
                day = day + j;
            }
            days.add(day);
        }
        days.stream().forEach(yesterDay -> {
            userService.listAllUserId().stream().forEach(id -> {
                //统计结果cashbooks：recordTime、cashType、dictId、cashCategory、amount
                List<Cashbook> cashbooks = cashbookService.statisticsDayAmount(id , yesterDay);
                //保存每人每天的账目数据到统计表
                statisticsService.saveOrUpdate(id , cashbooks);
            });
        });
    }*/

    /*
    @Autowired
    InitLoaderAsync loaderAsync;

    @Test
    public void testAsyncFuture() throws InterruptedException,ExecutionException{

        LogUtil.info("开始async");

        Future<List<String>> future1 = loaderAsync.getList();
        Future<List<String>> future2 = loaderAsync.getList2();

        for(int i=0; i < 10;i++){
            LogUtil.info("i：" + i);
        }
        List<String> list1 = future1.get();
        LogUtil.info("list size1：" + list1.size());
        List<String> list2 = future1.get();
        LogUtil.info("list size2：" + list2.size());
    }

    @Test
    public void testHashMap() {
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", "zhuyu");
        userMap.put("name", "zhangsan");
        System.out.println(userMap.get("name"));

        userMap.remove("name");

        System.out.println(userMap.get("name"));
    }


    @Test
    public void testJoinDouhao() {
        List<String> roles = new ArrayList<>();
        roles.add("1");
        roles.add("2");
        roles.add("3");
        roles.add("4");

        String result = roles.stream().collect(Collectors.joining(","));
        System.out.println(result);
    }

    @Test
    public void testLambda() {
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

    private List<Transaction> initData() {
        Trader raoul = new Trader("Raoul", "cambridge");
        Trader mario = new Trader("Mario", "milan");
        Trader alan = new Trader("Alan", "cambridge");
        Trader brian = new Trader("Brian", "cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
        return transactions;
    }

    public class Trader {
        private String name;
        private String city;

        public Trader() {
        }

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

    public class Transaction {
        private Trader trader;
        private int year;
        private int value;

        public Transaction() {
        }

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
    */
}
