package com.cardinalis.userservice.security;
import com.cardinalis.userservice.exception.ApiRequestException;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.repository.projection.UserPrincipalProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserPrincipalProjection user = userRepository.findUserPrincipalByEmail(email)
                .orElseThrow(() -> new ApiRequestException("User not found", HttpStatus.NOT_FOUND));

        if (user.getActivationCode() != null) {
            throw new LockedException("email not activated");
        }
        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword());
    }
}