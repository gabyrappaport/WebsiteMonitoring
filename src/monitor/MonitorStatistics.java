package monitor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import classes.Website;
import dao.CheckDao;
import dao.DAOException;
import dao.DAOFactory;

/**
 * MonitorStatistics is a link datatier and retrieve the statistics on a specific website.
 * @author gabriellerappaport
 *
 */
public class MonitorStatistics {
	private CheckDao checkDao;
	private Integer period;
	private Integer timeframe;
	private Website website;

	public MonitorStatistics(Website website, Integer period, Integer timeframe) {
		super();
		DAOFactory instance = DAOFactory.getInstance();
		this.checkDao = instance.getCheckDao();

		this.website = website;
		this.period = period;
		this.timeframe = timeframe;
	}

	public Map<java.util.Date, Double> availability() {
		Map<java.util.Date, Double> availability = new HashMap<>();
		try {
			availability = this.checkDao.readAvailability(this.website.getIdWebsite(), this.period, this.timeframe);
		} catch (DAOException e) {
			return availability;
		}
		return availability;
	}

	public Map<java.util.Date, Integer> computeAvgResponseTime() {
		Map<java.util.Date, Integer> avgResponseTime = new HashMap<>();
		try {
			avgResponseTime = this.checkDao.readAvgResponseTime(this.website.getIdWebsite(), this.period,
					this.timeframe);
		} catch (DAOException e) {
			return avgResponseTime;
		}
		return avgResponseTime;
	}

	public Map<java.util.Date, Integer> computeMaxResponseTime() {
		Map<java.util.Date, Integer> maxResponseTime = new HashMap<>();
		try {
			maxResponseTime = this.checkDao.readMaxResponseTime(this.website.getIdWebsite(), this.period,
					this.timeframe);
		} catch (DAOException e) {
			return maxResponseTime;
		}
		return maxResponseTime;
	}

	public Map<Date, Map<Integer, Integer>> countResponseCode() {
		Map<Date, Map<Integer, Integer>> countResponseCode = new HashMap<>();
		try {
			countResponseCode = this.checkDao.readCodesCount(this.website.getIdWebsite(), this.period, this.timeframe);
		} catch (DAOException e) {
			return countResponseCode;
		}
		return countResponseCode;
	}

	public CheckDao getCheckDao() {
		return this.checkDao;
	}

	public Website getCurrentWebsite() {
		return this.website;
	}

	public Integer getPeriod() {
		return this.period;
	}

	public Integer getTimeframe() {
		return this.timeframe;
	}

	public Website getWebsite() {
		return this.website;
	}

	public void setCheckDao(CheckDao checkDao) {
		this.checkDao = checkDao;
	}

	public void setCurrentWebsite(Website website) {
		this.website = website;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public void setTimeframe(Integer timeframe) {
		this.timeframe = timeframe;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

}
