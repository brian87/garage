package com.apress.service.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;

import com.apress.domain.Booking;
import com.apress.dto.AuditDTO;
import com.apress.dto.BookingDTO;

@Mapper(componentModel = "spring")
public interface BookingMapper {

	public BookingDTO toBookingDTO(Booking booking);

	public Booking toBooking(BookingDTO bookingDTO);

	public Collection<BookingDTO> toBookingDTOs(Collection<Booking> bookings);

	public AuditDTO toAuditDTO(Booking booking);

}
