/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ViewScoped
public class CalendarView implements Serializable {
 
    private Date date;
    private Date dateDe;
    private Date dateTimeDe;
    private List<Date> multi;
    private List<Date> range;
    private List<Date> invalidDates;
    private List<Integer> invalidDays;
    private Date minDate;
    private Date maxDate;
    private Date minTime;
    private Date maxTime;
    private Date minDateTime;
    private Date maxDateTime;
 
    @PostConstruct
    public void init() {
        invalidDates = new ArrayList<>();
        Date today = new Date();
        invalidDates.add(today);
        long oneDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < 5; i++) {
            invalidDates.add(new Date(invalidDates.get(i).getTime() + oneDay));
        }
 
        invalidDays = new ArrayList<>();
        invalidDays.add(0); /* the first day of week is disabled */
        invalidDays.add(3);
 
        minDate = new Date(today.getTime() - (365 * oneDay));
        maxDate = new Date(today.getTime() + (365 * oneDay));
 
        Calendar tmp = Calendar.getInstance();
        tmp.set(Calendar.HOUR_OF_DAY, 9);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        minTime = tmp.getTime();
 
        tmp = Calendar.getInstance();
        tmp.set(Calendar.HOUR_OF_DAY, 17);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        tmp.set(Calendar.MILLISECOND, 0);
        maxTime =  tmp.getTime();
 
        minDateTime = new Date(today.getTime() - (7 * oneDay));
        maxDateTime = new Date(today.getTime() + (7 * oneDay));
 
        dateDe = GregorianCalendar.getInstance().getTime();
        dateTimeDe = GregorianCalendar.getInstance().getTime();
    }
 
    public void onDateSelect(SelectEvent<Date> event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
 
    public Date getDate() {
        return date;
    }
 
    public void setDate(Date date) {
        this.date = date;
    }

    private static class SelectEvent<T> {

        public SelectEvent() {
        }

        private Object getObject() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}