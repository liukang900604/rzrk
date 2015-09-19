package com.rzrk.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rzrk.dao.MemberDao;
import com.rzrk.dao.ReservationsDao;
import com.rzrk.entity.Reservations;
import com.rzrk.service.ReservationsService;

@Service("reservationsServiceImpl")
public class ReservationsServiceImpl extends BaseServiceImpl<Reservations,String> implements ReservationsService{
	@Resource(name = "reservationsDaoImpl")
	private ReservationsDao reservationsDao;

	@Resource(name = "reservationsDaoImpl")
	public void setBaseDao(ReservationsDao reservationsDao) {
		super.setBaseDao(reservationsDao);
	}

}
