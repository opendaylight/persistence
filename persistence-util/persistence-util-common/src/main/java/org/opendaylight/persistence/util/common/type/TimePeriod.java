/**
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.persistence.util.common.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Time period.
 * 
 * @author Fabiel Zuniga
 */
public final class TimePeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    // Time zone is not included because Java Date is in Universal time. Thus the time period could
    // be converted to any time zone.
    private final Date startTime;
    private final Date endTime;

    /**
     * Constructs a new time period.
     *
     * @param startTime
     *            start time
     * @param endTime
     *            end time
     */
    public TimePeriod(@Nonnull Date startTime, @Nonnull Date endTime) {
        Preconditions.checkNotNull(startTime, "startTime");
        Preconditions.checkNotNull(endTime, "endTime");
        Preconditions
                .checkArgument(
                        startTime.compareTo(endTime) < 0,
                        "startTime must happen before endTime: [startTime = {} endTime = {}]",
                        startTime, endTime);

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets the start time.
     *
     * @return the start time
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * Gets the end time.
     *
     * @return the end time
     */
    public Date getEndTime() {
        return this.endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.startTime, this.endTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        TimePeriod other = (TimePeriod) obj;

        if (!this.startTime.equals(other.startTime)) {
            return false;
        }

        if (!this.endTime.equals(other.endTime)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("startTime", this.startTime).add("endTime", this.endTime)
                .toString();
    }

    /**
     * Verifies whether the given dates conform a valid time period.
     *
     * @param startTime
     *            start time
     * @param endTime
     *            end time
     * @return {@code true} if {@code startTime} and {@code endTime} conform a valid time period, {@code false}
     *         otherwise
     */
    public static boolean isValidPeriod(Date startTime, Date endTime) {
        if (startTime == null) {
            return false;
        }

        if (endTime == null) {
            return false;
        }

        return startTime.compareTo(endTime) < 0;
    }

    /**
     * Gets the one-hour period the given point in time belongs to.
     *
     * @param time
     *            reference point in time
     * @param timeZone
     *            time zone. See {@link TimeZone#getDefault()}
     * @return a time period where the start time is the beginning of the day {@code time} belongs to and the end time
     *         is the end of the day
     */
    public static TimePeriod getHourPeriod(Date time, TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar reference = Calendar.getInstance(timeZone);
        reference.setTime(time);

        Calendar startTime = Calendar.getInstance(timeZone);
        startTime.set(reference.get(Calendar.YEAR),
                reference.get(Calendar.MONTH), reference.get(Calendar.DATE),
                reference.get(Calendar.HOUR), 0, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        Calendar endTime = Calendar.getInstance(timeZone);
        endTime.setTime(startTime.getTime());
        endTime.add(Calendar.HOUR, 1);
        endTime.add(Calendar.MILLISECOND, -1);

        return new TimePeriod(startTime.getTime(), endTime.getTime());
    }

    /**
     * Gets the one-day period the given point in time belongs to.
     *
     * @param time
     *            reference point in time
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a time period where the start time is the beginning of the day {@code time} belongs to and the end time
     *         is the end of the day
     */
    public static TimePeriod getDayPeriod(Date time, TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar reference = Calendar.getInstance(timeZone);
        reference.setTime(time);

        Calendar startTime = Calendar.getInstance(timeZone);
        startTime.set(reference.get(Calendar.YEAR),
                reference.get(Calendar.MONTH), reference.get(Calendar.DATE), 0,
                0, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        Calendar endTime = Calendar.getInstance(timeZone);
        endTime.setTime(startTime.getTime());
        endTime.add(Calendar.DATE, 1);
        endTime.add(Calendar.MILLISECOND, -1);

        return new TimePeriod(startTime.getTime(), endTime.getTime());
    }

    /**
     * Gets the one-week period the given point in time belongs to.
     *
     * @param time
     *            reference point in time
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a time period where the start time is the beginning of the week {@code time} belongs to and the end time
     *         is the end of the week
     */
    public static TimePeriod getWeekPeriod(Date time, TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar reference = Calendar.getInstance(timeZone);
        reference.setTime(time);

        Calendar startTime = Calendar.getInstance(timeZone);
        startTime.set(reference.get(Calendar.YEAR),
                reference.get(Calendar.MONTH), reference.get(Calendar.DATE), 0,
                0, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime
                .add(Calendar.DATE, -(reference.get(Calendar.DAY_OF_WEEK) - 1));

        Calendar endTime = Calendar.getInstance(timeZone);
        endTime.setTime(startTime.getTime());
        endTime.add(Calendar.DATE, 7);
        endTime.add(Calendar.MILLISECOND, -1);

        return new TimePeriod(startTime.getTime(), endTime.getTime());
    }

    /**
     * Gets the week the given point in time belongs to. The week starts at Sunday.
     *
     * @param time
     *            reference point in time
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return an array of seven elements containing the starting point of the days of the week (Sunday - Saturday)
     */
    public static Date[] getWeekDays(Date time, TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        TimePeriod weekPeriod = getWeekPeriod(time, timeZone);

        Date[] week = new Date[7];
        for (int i = 0; i < week.length; i++) {
            Calendar currentDay = Calendar.getInstance(timeZone);
            currentDay.setTime(weekPeriod.getStartTime());
            currentDay.add(Calendar.DATE, i);
            week[i] = currentDay.getTime();
        }

        return week;
    }

    /**
     * Gets the one-month period the given point in time belongs to.
     *
     * @param time
     *            reference point in time
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a time period where the start time is the beginning of the month {@code time} belongs to and the end time
     *         is the end of the month
     */
    public static TimePeriod getMonthPeriod(Date time, TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar reference = Calendar.getInstance(timeZone);
        reference.setTime(time);

        Calendar startTime = Calendar.getInstance(timeZone);
        startTime.set(reference.get(Calendar.YEAR),
                reference.get(Calendar.MONTH), 1, 0, 0, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        Calendar endTime = Calendar.getInstance(timeZone);
        endTime.setTime(startTime.getTime());
        endTime.add(Calendar.MONTH, 1);
        endTime.add(Calendar.MILLISECOND, -1);

        return new TimePeriod(startTime.getTime(), endTime.getTime());
    }

    /**
     * Gets the one-year period the given point in time belongs to.
     *
     * @param time
     *            reference point in time
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a time period where the start time is the beginning of the year {@code time} belongs to and the end time
     *         is the end of the year
     */
    public static TimePeriod getYearPeriod(Date time, TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar reference = Calendar.getInstance(timeZone);
        reference.setTime(time);

        Calendar startTime = Calendar.getInstance(timeZone);
        startTime.set(reference.get(Calendar.YEAR), 0, 1, 0, 0, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        Calendar endTime = Calendar.getInstance(timeZone);
        endTime.setTime(startTime.getTime());
        endTime.add(Calendar.YEAR, 1);
        endTime.add(Calendar.MILLISECOND, -1);

        return new TimePeriod(startTime.getTime(), endTime.getTime());
    }

    private static List<TimePeriod> generatePeriodsFromPoints(
            List<Date> timePoints, TimeZone timeZone) {
        List<TimePeriod> samples = new ArrayList<TimePeriod>();

        for (int i = 0; i < timePoints.size() - 2; i++) {
            Calendar endTime = Calendar.getInstance(timeZone);
            endTime.setTime(timePoints.get(i + 1));
            endTime.add(Calendar.MILLISECOND, -1);

            samples.add(new TimePeriod(timePoints.get(i), endTime.getTime()));
        }

        samples.add(new TimePeriod(timePoints.get(timePoints.size() - 2),
                timePoints.get(timePoints.size() - 1)));

        return samples;
    }

    /**
     * Breaks the time period down to days.
     *
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a sorted list of time periods for each day
     */
    public List<TimePeriod> breakDownHourly(TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar init = Calendar.getInstance(timeZone);
        init.setTime(this.startTime);

        Calendar endCalendar = Calendar.getInstance(timeZone);
        endCalendar.setTime(this.endTime);
        Date end = endCalendar.getTime();

        List<Date> timePoints = new ArrayList<Date>();
        timePoints.add(init.getTime());

        Calendar next = Calendar.getInstance(timeZone);
        next.set(init.get(Calendar.YEAR), init.get(Calendar.MONTH),
                init.get(Calendar.DATE), init.get(Calendar.HOUR), 0, 0);
        next.set(Calendar.MILLISECOND, 0);
        next.add(Calendar.HOUR, 1);

        while (next.getTime().compareTo(end) < 0) {
            timePoints.add(next.getTime());
            next.add(Calendar.HOUR, 1);
        }

        timePoints.add(end);

        return generatePeriodsFromPoints(timePoints, timeZone);
    }

    /**
     * Breaks the time period down to days.
     *
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a sorted list of time periods for each day
     */
    public List<TimePeriod> breakDownDaily(TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar init = Calendar.getInstance(timeZone);
        init.setTime(this.startTime);

        Calendar endCalendar = Calendar.getInstance(timeZone);
        endCalendar.setTime(this.endTime);
        Date end = endCalendar.getTime();

        List<Date> timePoints = new ArrayList<Date>();
        timePoints.add(init.getTime());

        Calendar next = Calendar.getInstance(timeZone);
        next.set(init.get(Calendar.YEAR), init.get(Calendar.MONTH),
                init.get(Calendar.DATE), 0, 0, 0);
        next.set(Calendar.MILLISECOND, 0);
        next.add(Calendar.DATE, 1);

        while (next.getTime().compareTo(end) < 0) {
            timePoints.add(next.getTime());
            next.add(Calendar.DATE, 1);
        }

        timePoints.add(end);

        return generatePeriodsFromPoints(timePoints, timeZone);
    }

    /**
     * Breaks the time period down to weeks.
     *
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a sorted list of time periods for each week
     */
    public List<TimePeriod> breakDownWeekly(TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar init = Calendar.getInstance(timeZone);
        init.setTime(this.startTime);

        Calendar endCalendar = Calendar.getInstance(timeZone);
        endCalendar.setTime(this.endTime);
        Date end = endCalendar.getTime();

        List<Date> timePoints = new ArrayList<Date>();
        timePoints.add(init.getTime());

        Calendar next = Calendar.getInstance(timeZone);
        next.set(init.get(Calendar.YEAR), init.get(Calendar.MONTH),
                init.get(Calendar.DATE), 0, 0, 0);
        next.set(Calendar.MILLISECOND, 0);
        next.add(Calendar.DATE, 8 - init.get(Calendar.DAY_OF_WEEK));

        while (next.getTime().compareTo(end) < 0) {
            timePoints.add(next.getTime());
            next.add(Calendar.DATE, 7);
        }

        timePoints.add(end);

        return generatePeriodsFromPoints(timePoints, timeZone);
    }

    /**
     * Breaks the time period down to months.
     *
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a sorted list of time periods for each month
     */
    public List<TimePeriod> breakDownMonthly(TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar init = Calendar.getInstance(timeZone);
        init.setTime(this.startTime);

        Calendar endCalendar = Calendar.getInstance(timeZone);
        endCalendar.setTime(this.endTime);
        Date end = endCalendar.getTime();

        List<Date> timePoints = new ArrayList<Date>();
        timePoints.add(init.getTime());

        Calendar next = Calendar.getInstance(timeZone);
        next.set(init.get(Calendar.YEAR), init.get(Calendar.MONTH), 1, 0, 0, 0);
        next.set(Calendar.MILLISECOND, 0);
        next.add(Calendar.MONTH, 1);

        while (next.getTime().compareTo(end) < 0) {
            timePoints.add(next.getTime());
            next.add(Calendar.MONTH, 1);
        }

        timePoints.add(end);

        return generatePeriodsFromPoints(timePoints, timeZone);
    }

    /**
     * Breaks the time period down to years.
     *
     * @param timeZone
     *            time zone; see {@link TimeZone#getDefault()}
     * @return a sorted list of time periods for each year
     */
    public List<TimePeriod> breakDownYearly(TimeZone timeZone) {
        if (timeZone == null) {
            // Null is not allowed as the default time zone to make time zone definition explicit.
            // These methods make no sense without time zone information, and it would be harder to
            // track issues related to time zone if null is allowed.
            throw new NullPointerException("timezone cannot be null");
        }

        Calendar init = Calendar.getInstance(timeZone);
        init.setTime(this.startTime);

        Calendar endCalendar = Calendar.getInstance(timeZone);
        endCalendar.setTime(this.endTime);
        Date end = endCalendar.getTime();

        List<Date> timePoints = new ArrayList<Date>();
        timePoints.add(init.getTime());

        Calendar next = Calendar.getInstance(timeZone);
        next.set(init.get(Calendar.YEAR), 0, 1, 0, 0, 0);
        next.set(Calendar.MILLISECOND, 0);
        next.add(Calendar.YEAR, 1);

        while (next.getTime().compareTo(end) < 0) {
            timePoints.add(next.getTime());
            next.add(Calendar.YEAR, 1);
        }

        timePoints.add(end);

        return generatePeriodsFromPoints(timePoints, timeZone);
    }
}
