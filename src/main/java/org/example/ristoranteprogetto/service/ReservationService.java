package org.example.ristoranteprogetto.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.ristoranteprogetto.mapper.ReservationMapper;
import org.example.ristoranteprogetto.model.dto.ReservationDTO;
import org.example.ristoranteprogetto.model.dto.UserDTO;
import org.example.ristoranteprogetto.model.entity.ReservationEntity;
import org.example.ristoranteprogetto.model.entity.TableEntity;
import org.example.ristoranteprogetto.model.entity.UserEntity;
import org.example.ristoranteprogetto.repository.ReservationRepository;
import org.example.ristoranteprogetto.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.example.ristoranteprogetto.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // uso questa soluzione perchè il professore ci ha sconsigliato di utilizzare Autowired
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;



    public List<ReservationEntity> getReservationsByDate(LocalDateTime date) {
        return reservationRepository.findByDataPrenotazione(date);
    }



    public List<TableEntity> getAvailableTables(LocalDateTime data, int numeroPersone) {
        List<TableEntity> allTables = tableRepository.findAll();
        return allTables.stream()
                .filter(t -> t.getPosti() >= numeroPersone)
                .filter(t -> reservationRepository.findByTableIdAndDataPrenotazione(t.getId(), data).isEmpty())
                .toList();
    }

    public Optional<ReservationEntity> getById(Long id) {
        return reservationRepository.findById(id);
    }


    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }



    @Transactional
    public ReservationDTO createReservation(ReservationDTO dto) {
            UserEntity user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            ReservationEntity reservation = new ReservationEntity();
            reservation.setDataPrenotazione(dto.getDataPrenotazione());
            reservation.setNumeroPersone(dto.getNumeroPersone());
            reservation.setUser(user);

            reservation = reservationRepository.save(reservation);
            return reservationMapper.toDto(reservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<ReservationDTO> getReservationsByUserId(UUID userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationDTO updateStatus(Long id, String status) {
        return reservationRepository.findById(id)
                .map(reservation -> {
                    reservation.setStatus(status);
                    ReservationEntity updated = reservationRepository.save(reservation);
                    return reservationMapper.toDto(updated);
                })
                .orElse(null);
    }
}
