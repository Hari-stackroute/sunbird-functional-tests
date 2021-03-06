package org.sunbird.integration.test.course.batch;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.sunbird.common.action.ContentStoreUtil;
import org.sunbird.common.action.OrgUtil;
import org.sunbird.common.action.UserUtil;
import org.sunbird.integration.test.common.BaseCitrusTestRunner;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateCourseBatchTest extends BaseCitrusTestRunner {

  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_NAME =
      "testCreateCourseBatchFailureWithoutName";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_COURSE_ID =
      "testCreateCourseBatchFailureWithoutCourseId";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_ENROLLMENTTYPE =
      "testCreateCourseBatchFailureWithoutEnrollmentType";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_ENROLLMENTTYPE =
      "testCreateCourseBatchFailureWithInvalidEnrollmentType";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_START_DATE =
      "testCreateCourseBatchFailureWithoutStartDate";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITH_PAST_START_DATE =
      "testCreateCourseBatchFailurePastStartDate";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_COURSE_ID =
      "testCreateCourseBatchFailureInvalidCourseId";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITH_PAST_END_DATE =
      "testCreateCourseBatchFailurePastEndDate";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITH_END_DATE_BEFORE_START_DATE =
      "testCreateCourseBatchFailureEndDateBeforeStartDate";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_CREATED_FOR =
      "testCreateCourseBatchFailureInviteOnlyWithInvalidCreatedFor";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_INVITE_ONLY_WITH_INVALID_MENTOR =
      "testCreateCourseBatchFailureInviteOnlyWithInvalidMentor";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_OPEN_BATCH_WITH_INVALID_MENTOR =
      "testCreateCourseBatchFailureOpenBatchWithInvalidMentor";
  public static final String TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_PARTICIPANT =
      "testCreateCourseBatchFailureInviteOnlyWithInvalidParticipant";

  public static final String TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_BATCH =
      "testCreateCourseBatchSuccessInviteOnlyBatch";
  public static final String TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_WITH_CREATED_FOR =
      "testCreateCourseBatchSuccessInviteOnlyWithCreatedFor";
  public static final String TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_WITH_MENTORS =
      "testCreateCourseBatchSuccessInviteOnlyWithMentors";
  public static final String TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_WITH_PARTICICPANTS =
      "testCreateCourseBatchSuccessInviteOnlyWithParticipants";
  public static final String TEST_CREATE_COURSE_BATCH_SUCCESS_OPEN_BATCH =
      "testCreateCourseBatchSuccessOpenBatch";
  public static final String TEST_CREATE_COURSE_BATCH_SUCCESS_OPEN_BATCH_WITH_MENTORS =
      "testCreateCourseBatchSuccessOpenBatchWithMentors";

  public static final String TEMPLATE_DIR = "templates/course/batch/create";
  public static final String TODAY_DATE = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

  private String getCreateCourseBatchUrl() {
    return getLmsApiUriPath("/api/course/v1/batch/create", "/v1/course/batch/create");
  }

  @DataProvider(name = "createCourseBatchFailureDataProvider")
  public Object[][] createCourseBatchFailureDataProvider() {

    return new Object[][] {
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_NAME, false, false, false, HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_COURSE_ID,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_ENROLLMENTTYPE,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_ENROLLMENTTYPE,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITHOUT_START_DATE,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITH_PAST_START_DATE,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_COURSE_ID,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITH_PAST_END_DATE,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITH_END_DATE_BEFORE_START_DATE,
        false,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_CREATED_FOR,
        true,
        false,
        false,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_INVITE_ONLY_WITH_INVALID_MENTOR,
        true,
        false,
        true,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_OPEN_BATCH_WITH_INVALID_MENTOR,
        true,
        false,
        true,
        HttpStatus.BAD_REQUEST
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_FAILURE_WITH_INVALID_PARTICIPANT,
        true,
        true,
        true,
        HttpStatus.NOT_FOUND
      }
    };
  }

  @Test(dataProvider = "createCourseBatchFailureDataProvider")
  @CitrusParameters({
    "testName",
    "isCourseIdRequired",
    "isOrganisationRequired",
    "isUserIdRequired",
    "httpStatusCode"
  })
  @CitrusTest
  public void testCreateCourseBatchFailure(
      String testName,
      boolean isCourseIdRequired,
      boolean isOrganisationRequired,
      boolean isUserIdRequired,
      HttpStatus httpStatusCode) {
    beforeTest(isCourseIdRequired, isOrganisationRequired, isUserIdRequired);
    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        httpStatusCode,
        RESPONSE_JSON);
  }

  @DataProvider(name = "createCourseBatchSuccessDataProvider")
  public Object[][] createCourseBatchSuccessDataProvider() {
    return new Object[][] {
      new Object[] {
        TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_BATCH, false, false, HttpStatus.OK
      },
      new Object[] {TEST_CREATE_COURSE_BATCH_SUCCESS_OPEN_BATCH, false, false, HttpStatus.OK},
      new Object[] {
        TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_WITH_CREATED_FOR, true, false, HttpStatus.OK
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_WITH_MENTORS, false, true, HttpStatus.OK
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_SUCCESS_OPEN_BATCH_WITH_MENTORS, false, true, HttpStatus.OK
      },
      new Object[] {
        TEST_CREATE_COURSE_BATCH_SUCCESS_INVITE_ONLY_WITH_PARTICICPANTS, true, true, HttpStatus.OK
      }
    };
  }

  @Test(dataProvider = "createCourseBatchSuccessDataProvider")
  @CitrusParameters({"testName", "isOrgIdRequired", "isUsrIdRequired", "httpStatusCode"})
  @CitrusTest
  public void testCreateCourseBatchSuccess(
      String testName,
      boolean isOrgIdRequired,
      boolean isUsrIdRequired,
      HttpStatus httpStatusCode) {
    beforeTest(true, isOrgIdRequired, isUsrIdRequired);

    performPostTest(
        this,
        TEMPLATE_DIR,
        testName,
        getCreateCourseBatchUrl(),
        REQUEST_JSON,
        MediaType.APPLICATION_JSON,
        true,
        httpStatusCode,
        RESPONSE_JSON);
  }

  public void beforeTest(
      boolean isCourseIdRequired, boolean isOrgIdRequired, boolean isUsrIdRequired) {
    getAuthToken(this, true);
    if (isOrgIdRequired) {
      variable("rootOrgChannel", OrgUtil.getRootOrgChannel());
      OrgUtil.getRootOrgId(this, testContext);
    }
    variable("startDate", TODAY_DATE);
    if (isCourseIdRequired) {
      // courseUnitId/resourceId is needed to be updated in context for creating course
      variable("courseUnitId", ContentStoreUtil.getCourseUnitId());
      variable("resourceId", ContentStoreUtil.getResourceId());
      String courseId = ContentStoreUtil.getCourseId(this, testContext);
      variable("courseId", courseId);
    }

    if (isUsrIdRequired) {
      UserUtil.createUserAndGetToken(this, testContext);
    }
  }
}
