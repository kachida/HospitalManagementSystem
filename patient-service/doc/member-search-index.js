memberSearchIndex = [{"p":"com.patientsvc.service","c":"IPatientService","l":"addPatient(PatientDto)","u":"addPatient(com.patientsvc.dto.PatientDto)"},{"p":"com.patientsvc.service","c":"PatientServiceImpl","l":"addPatient(PatientDto)","u":"addPatient(com.patientsvc.dto.PatientDto)"},{"p":"com.patientsvc.exceptions","c":"ApiError","l":"addValidationError(ConstraintViolation<?>)","u":"addValidationError(javax.validation.ConstraintViolation)"},{"p":"com.patientsvc.exceptions","c":"ApiError","l":"addValidationErrors(List<FieldError>)","u":"addValidationErrors(java.util.List)"},{"p":"com.patientsvc.exceptions","c":"ApiError","l":"addValidationErrors(Set<ConstraintViolation<?>>)","u":"addValidationErrors(java.util.Set)"},{"p":"com.patientsvc.exceptions","c":"ApiError","l":"ApiError(HttpStatus)","u":"%3Cinit%3E(org.springframework.http.HttpStatus)"},{"p":"com.patientsvc.exceptions","c":"ApiError","l":"ApiError(HttpStatus, String, Throwable)","u":"%3Cinit%3E(org.springframework.http.HttpStatus,java.lang.String,java.lang.Throwable)"},{"p":"com.patientsvc.exceptions","c":"ApiError","l":"ApiError(HttpStatus, Throwable)","u":"%3Cinit%3E(org.springframework.http.HttpStatus,java.lang.Throwable)"},{"p":"com.patientsvc.exceptions","c":"ApiSubError","l":"ApiSubError()","u":"%3Cinit%3E()"},{"p":"com.patientsvc.configuration","c":"SecurityConfiguration","l":"authenticationManagerBean()"},{"p":"com.patientsvc.models","c":"AuthenticationRequest","l":"AuthenticationRequest()","u":"%3Cinit%3E()"},{"p":"com.patientsvc.models","c":"AuthenticationResponse","l":"AuthenticationResponse(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"com.patientsvc.configuration","c":"SwaggerConfig","l":"AUTHORIZATION_HEADER"},{"p":"com.patientsvc.feign","c":"UserServiceClientConfiguration","l":"bearerTokenRequestInterceptor()"},{"p":"com.patientsvc.controller","c":"PatientController","l":"createAuthenticationToken(AuthenticationRequest)","u":"createAuthenticationToken(com.patientsvc.models.AuthenticationRequest)"},{"p":"com.patientsvc.configuration","c":"SwaggerConfig","l":"DEFAULT_INCLUDE_PATTERN"},{"p":"com.patientsvc.service","c":"IPatientService","l":"deletePatient(long)"},{"p":"com.patientsvc.service","c":"PatientServiceImpl","l":"deletePatient(long)"},{"p":"com.patientsvc.controller","c":"PatientController","l":"deletePatientRecord(long)"},{"p":"com.patientsvc.security","c":"JwtUtil","l":"extractClaim(String, Function<Claims, T>)","u":"extractClaim(java.lang.String,java.util.function.Function)"},{"p":"com.patientsvc.security","c":"JwtUtil","l":"extractExpiration(String)","u":"extractExpiration(java.lang.String)"},{"p":"com.patientsvc.security","c":"JwtUtil","l":"extractUsername(String)","u":"extractUsername(java.lang.String)"},{"p":"com.patientsvc.security","c":"JwtUtil","l":"generateToken(UserDetails)","u":"generateToken(org.springframework.security.core.userdetails.UserDetails)"},{"p":"com.patientsvc.controller","c":"PatientController","l":"getAllPatients(int, int, String)","u":"getAllPatients(int,int,java.lang.String)"},{"p":"com.patientsvc.service","c":"IPatientService","l":"getAllPatients(int, int, String)","u":"getAllPatients(int,int,java.lang.String)"},{"p":"com.patientsvc.service","c":"PatientServiceImpl","l":"getAllPatients(int, int, String)","u":"getAllPatients(int,int,java.lang.String)"},{"p":"com.patientsvc.models","c":"AuthenticationResponse","l":"getJwt()"},{"p":"com.patientsvc.service","c":"IPatientService","l":"getPatientById(long)"},{"p":"com.patientsvc.service","c":"PatientServiceImpl","l":"getPatientById(long)"},{"p":"com.patientsvc.controller","c":"PatientController","l":"getUserById(long)"},{"p":"com.patientsvc.feign","c":"UserServiceClient","l":"getUserById(long)"},{"p":"com.patientsvc.service","c":"PatientServiceImpl","l":"getUserServiceData(long)"},{"p":"com.patientsvc.filter","c":"JwtRequestFilter","l":"JwtRequestFilter(MyUserDetailService, JwtUtil)","u":"%3Cinit%3E(com.patientsvc.service.MyUserDetailService,com.patientsvc.security.JwtUtil)"},{"p":"com.patientsvc.security","c":"JwtUtil","l":"JwtUtil(YamlProperties)","u":"%3Cinit%3E(com.patientsvc.models.YamlProperties)"},{"p":"com.patientsvc.service","c":"MyUserDetailService","l":"loadUserByUsername(String)","u":"loadUserByUsername(java.lang.String)"},{"p":"com.patientsvc.aspect","c":"Logger","l":"log(ProceedingJoinPoint)","u":"log(org.aspectj.lang.ProceedingJoinPoint)"},{"p":"com.patientsvc.aspect","c":"Logger","l":"Logger()","u":"%3Cinit%3E()"},{"p":"com.patientsvc","c":"PatientServiceApplication","l":"main(String[])","u":"main(java.lang.String[])"},{"p":"com.patientsvc.service","c":"MyUserDetailService","l":"MyUserDetailService(YamlProperties)","u":"%3Cinit%3E(com.patientsvc.models.YamlProperties)"},{"p":"com.patientsvc.models","c":"Patient","l":"Patient()","u":"%3Cinit%3E()"},{"p":"com.patientsvc.configuration","c":"SwaggerConfig","l":"patientApi()"},{"p":"com.patientsvc.controller","c":"PatientController","l":"PatientController(IPatientService, AuthenticationManager, MyUserDetailService, JwtUtil)","u":"%3Cinit%3E(com.patientsvc.service.IPatientService,org.springframework.security.authentication.AuthenticationManager,com.patientsvc.service.MyUserDetailService,com.patientsvc.security.JwtUtil)"},{"p":"com.patientsvc.dto","c":"PatientDto","l":"PatientDto()","u":"%3Cinit%3E()"},{"p":"com.patientsvc","c":"PatientServiceApplication","l":"PatientServiceApplication()","u":"%3Cinit%3E()"},{"p":"com.patientsvc.service","c":"PatientServiceImpl","l":"PatientServiceImpl(IPatientRepository, UserServiceClient, ModelMapper)","u":"%3Cinit%3E(com.patientsvc.repository.IPatientRepository,com.patientsvc.feign.UserServiceClient,org.modelmapper.ModelMapper)"},{"p":"com.patientsvc.exceptions","c":"RestExceptionHandler","l":"RestExceptionHandler()","u":"%3Cinit%3E()"},{"p":"com.patientsvc.controller","c":"PatientController","l":"savePatientRecord(PatientDto)","u":"savePatientRecord(com.patientsvc.dto.PatientDto)"},{"p":"com.patientsvc.configuration","c":"SecurityConfiguration","l":"SecurityConfiguration(MyUserDetailService, JwtRequestFilter)","u":"%3Cinit%3E(com.patientsvc.service.MyUserDetailService,com.patientsvc.filter.JwtRequestFilter)"},{"p":"com.patientsvc.configuration","c":"SwaggerConfig","l":"SwaggerConfig()","u":"%3Cinit%3E()"},{"p":"com.patientsvc.service","c":"IPatientService","l":"updatePatient(PatientDto, long)","u":"updatePatient(com.patientsvc.dto.PatientDto,long)"},{"p":"com.patientsvc.service","c":"PatientServiceImpl","l":"updatePatient(PatientDto, long)","u":"updatePatient(com.patientsvc.dto.PatientDto,long)"},{"p":"com.patientsvc.controller","c":"PatientController","l":"updatePatientRecord(PatientDto, long)","u":"updatePatientRecord(com.patientsvc.dto.PatientDto,long)"},{"p":"com.patientsvc.models","c":"User","l":"User()","u":"%3Cinit%3E()"},{"p":"com.patientsvc.feign","c":"UserServiceClientConfiguration","l":"UserServiceClientConfiguration(YamlProperties)","u":"%3Cinit%3E(com.patientsvc.models.YamlProperties)"},{"p":"com.patientsvc.security","c":"JwtUtil","l":"validateToken(String, UserDetails)","u":"validateToken(java.lang.String,org.springframework.security.core.userdetails.UserDetails)"},{"p":"com.patientsvc.models","c":"YamlProperties","l":"YamlProperties()","u":"%3Cinit%3E()"}];updateSearchResults();