package com.wecare.booking.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wecare.booking.entity.BookingEntity;

public interface BookRepository extends JpaRepository< BookingEntity, Integer>{
	@Query(value="SELECT b FROM bookingtable b.user_id = ?1", nativeQuery =true)
	Optional<BookingEntity> findByUserId(String userId);
	@Query(value="SELECT * FROM bookingtable b WHERE b.user_id = ?1 AND b.appointment_date >= ?2 ", nativeQuery =true)
	List<BookingEntity> findBookingByUserId(String userId, LocalDate today);
	@Query(value="SELECT * FROM bookingtable b WHERE b.coach_id = ?1 AND b.appointment_date >= ?2 ", nativeQuery =true)
	List<BookingEntity> findBookingByCoachId(String coachId, LocalDate today);
	@Query(value="SELECT * FROM bookingtable b WHERE b.user_id = ?1 AND b.appointment_date = ?2 AND b.slot = ?3", nativeQuery =true)
	BookingEntity findAllBookings(String userId, LocalDate appointmentDate, String slot);
}
