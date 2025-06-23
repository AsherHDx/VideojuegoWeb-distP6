package controller;

import jakarta.transaction.Transactional;
import model.PlayerRepository;
import model.EnemyRepository;
import model.Player;
import model.Enemy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final EnemyRepository enemyRepository;

    public PlayerService (PlayerRepository playerRepository, EnemyRepository enemyRepository){
        this.playerRepository = playerRepository;
        this.enemyRepository = enemyRepository;
    }

    /*
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }*/


    public Player getPlayerByName(String username) {
        Optional<Player> playerOpt = playerRepository.findByName(username);
        return playerOpt.orElse(null);
    }

    @Transactional
    public String addPlayer(Player player) {
        if(player.getName()==null || player.getName().isEmpty()){
            return "Es posible que el nombre no se esté recibiendo correctamente";
        }
        System.out.println("Se supone que sí hay nombre");
        playerRepository.save(player);
        return "Jugador añadido exitosamente";
    }

    public String movePlayer(String username, int posX, int posY){
        if(username==null || username.isEmpty()){
            return "Es posible que el nombre no se esté recibiendo correctamente";
        }
        Optional<Player> existingPlayerOpt = playerRepository.findByName(username);
        if(existingPlayerOpt.isEmpty()){
            return "Jugador no encoentrado";
        }
        Player existingPlayer = existingPlayerOpt.get();
        existingPlayer.setPosX(posX);
        existingPlayer.setPosY(posY);
        playerRepository.save(existingPlayer);

        List<Enemy> lst = enemyRepository.findAll();
        for(Enemy enemy: lst){
            if(enemy.getPosX()==existingPlayer.getPosX() && enemy.getPosY()==existingPlayer.getPosY()){
                return username+" fue movido a ("+posX+","+posY+") de manera exitosa. Está parado sobre un enemigo";
            }
        }
        return username+" fue movido a ("+posX+","+posY+") de manera exitosa";
    }

    public String statusPlayer(String username){
        Optional<Player> playerOpt = playerRepository.findByName(username);
        if(playerOpt.isEmpty()) return "No se encontró el jugador";
        Player player = playerOpt.get();
        return "El jugador "+player.getName()+" está en la posición ("+player.getPosX()+","+player.getPosY()+")";
    }

    public String attack(String username){
        if(username==null || username.isEmpty()){
            return "Es posible que el nombre no se esté recibiendo correctamente";
        }
        Optional<Player> existingPlayerOpt = playerRepository.findByName(username);
        if(existingPlayerOpt.isEmpty()){
            return "Jugador no encoentrado";
        }
        Player existingPlayer = existingPlayerOpt.get();

        List<Enemy> lst = enemyRepository.findAll();
        for(Enemy enemy: lst){
            if(enemy.getPosX()==existingPlayer.getPosX() && enemy.getPosY()==existingPlayer.getPosY()){
                enemyRepository.delete(enemy);
                return "Ataque realizado con éxito. Haz eliminado al enemigo";
            }
        }
        return "No se pued erealizar el ataque, busca a un enemigo";
    }

    @Transactional
    public String deletePlayer(String username) {
        System.out.print("Ya en el service, antes del if");
        if(playerRepository.deleteByName(username)>0) return "Jugador "+username+" eliminado correctamente";
        return "La base de datos no ha sufrido cambios";
    }
}
