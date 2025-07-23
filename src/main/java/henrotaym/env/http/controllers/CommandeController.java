package henrotaym.env.http.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import henrotaym.env.enums.ProfileName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@Profile(ProfileName.HTTP)
@RequestMapping("commandes")
public class CommandeController {}
