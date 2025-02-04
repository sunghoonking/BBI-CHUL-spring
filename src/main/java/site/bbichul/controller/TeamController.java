package site.bbichul.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.bbichul.dto.TeamProgressbarResponseDto;
import site.bbichul.dto.TeamRequestDto;
import site.bbichul.dto.TeamTaskRequestDto;
import site.bbichul.models.Team;
import site.bbichul.models.TeamTask;
import site.bbichul.models.User;
import site.bbichul.security.UserDetailsImpl;
import site.bbichul.service.TeamService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    @Operation(description = "팀 소속 여부 확인", method = "GET")
    @GetMapping("/teams")
    public Object checkTeam(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {}] Request GET /api/teams HTTP/1.1", userDetails.getUsername());
        return teamService.checkTeam(userDetails.getUser());
    }

    @Operation(description = "팀 만들기", method = "POST")
    @PostMapping("/teams")
    public Team createTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {}] Request POST /api/teams HTTP/1.1", userDetails.getUsername());
        return teamService.createTeam(teamRequestDto, userDetails.getUser());
    }

    @Operation(description = "to do list의 task 저장", method = "POST")
    @PostMapping("/teams/task")
    public TeamTask addTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        log.info("[TEAMID : {}] Request POST /api/teams/task HTTP/1.1", teamTaskRequestDto.getTeamId());
        return teamService.addTask(teamTaskRequestDto);
    }

    @Operation(description = "to do list의 task 내용 변경", method = "PUT")
    @PutMapping("/teams/task")
    public void updateTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        log.info("PUT /api/teams/task HTTP/1.1");
        teamService.updateTask(teamTaskRequestDto);
    }

    @Operation(description = "to do list의 task 조회", method = "GET")
    @GetMapping("/teams/task")
    public List<TeamTask> showTask(@RequestParam("teamid") final Long teamId) {
        log.info("[TEAMID : {}] Request GET /api/teams/task HTTP/1.1", teamId);
        return teamService.showTask(teamId);
    }

    @Operation(description = "to do list의 task 삭제", method = "DELETE")
    @DeleteMapping("/teams/task")
    public void deleteTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        log.info("DELETE /api/teams/task HTTP/1.1");
        teamService.deleteTask(teamTaskRequestDto);
    }

    @Operation(description = "to do list의 task 상태 변경", method = "PUT")
    @PutMapping("/teams/tasks/status")
    public void changeTask(@RequestBody TeamTaskRequestDto teamTaskRequestDto) {
        log.info("PUT /api/teams/tasks/status HTTP/1.1");
        teamService.changeTask(teamTaskRequestDto);
    }

    @Operation(description = "팀원 출결 현황 조회", method = "GET")
    @GetMapping("/teams/status")
    public List<User> checkStatus(@RequestParam("teamid") final Long teamId) {
        log.info("[TEAMID : {}] Request GET /api/teams/tasks/status HTTP/1.1", teamId);
        return teamService.checkStatus(teamId);
    }

    @Operation(description = "팀 가입", method = "POST")
    @PostMapping("/teams/signup")
    public Team signupTeam(@RequestBody TeamRequestDto teamRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("[USER : {} Request] POST /api/teams/signup HTTP/1.1", userDetails.getUsername());
        return teamService.signupTeam(teamRequestDto, userDetails.getUser());
    }

    @Operation(description = "팀명 중복 조회", method = "POST")
    @PostMapping("/teams/checkname")
    public Boolean checkName(@RequestBody TeamRequestDto teamRequestDto) {
        log.info("POST /api/teams/checkname HTTP/1.1");
        return teamService.checkName(teamRequestDto);
    }

    @Operation(description = "to do list의 전체 진행상황 그래프 조회", method = "GET")
    @GetMapping("/teams/task/graph")
    public TeamProgressbarResponseDto getTeamProgressbar(@RequestParam("teamid") final Long teamId, TeamProgressbarResponseDto teamProgressbarResponseDto) {
        log.info("[TEAMID : {}] Request GET /api/teams/task/graph HTTP/1.1", teamId);
        return teamService.getTeamProgressbar(teamId, teamProgressbarResponseDto);
    }
}