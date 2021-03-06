package com.xairlab.otus.patterns;

import com.xairlab.otus.patterns.atm.ATM;
import com.xairlab.otus.patterns.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private static Logger logger = LoggerFactory.getLogger(Department.class);

    private List<ATM> atms;

    public Department() {
        atms = new ArrayList<>();
    }

    public void addATM(ATM atm) {
        logger.info("Добавили банкомат в департамент");
        atms.add(atm);
    }

    public int getDepartmentTotal() {
        logger.info("Собрали деньги с банкматов");
        return getTotalATM();
    }

    public void saveState(){
        logger.info("Сохранили состояние банкоматов");
        for (ATM atm : atms) {
            atm.executeDepartmentCommand(new SaveState());
        }
    }

    public void restoreState(){
        logger.info("Восстановили состояние банкоматов");
        for (ATM atm : atms) {
            atm.executeDepartmentCommand(new RestoreState());
        }
    }

    public void maintanceOn(){
        logger.info("Отправили банкоматы на тенхническле обслуживание");
        for (ATM atm : atms) {
            atm.executeDepartmentCommand(new MaintenanceOn());
        }
    }

    public void maintanceOff(){
        logger.info("Вернули банкоматы с технического обслуживания");
        for (ATM atm : atms) {
            atm.executeDepartmentCommand(new MaintenanceOff());
        }
    }

    private int getTotalATM() {
        int sum = 0;
        for (ATM atm : atms) {
            GetCash command = new GetCash();
            atm.executeDepartmentCommand(command);
            sum += command.getSum();
        }
        return sum;
    }
}
