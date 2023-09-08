package data.mapper

/**
 * Created by Ashwani Kumar Singh on 07,September,2023.
 */
data class JSampleData(
    val _id: Id,
    val balance: String,
    val balanceReductionAmount: String,
    val createdChannelId: CreatedChannelId,
    val createdDate: CreatedDate,
    val createdTransactionId: String,
    val createdUserId: CreatedUserId,
    val daysDelinquent: Int,
    val disbursementDate: DisbursementDate,
    val expirationDate: ExpirationDate,
    val geographicState: String,
    val lastPaycheckDate: LastPaycheckDate,
    val loanStatus: String,
    val months: Int,
    val nextPaymentDueDate: NextPaymentDueDate,
    val optionsGenerated: Int,
    val originalInterestRate: String,
    val originalMaturityDate: OriginalMaturityDate,
    val originalPaymentAmount: String,
    val originalRemainingLoanTermPmts: Int,
    val originalTotalLoanTermMonths: Int,
    val originalTotalLoanTermPmts: Int,
    val paymentDay: Int,
    val paymentFrequency: Int,
    val paymentRequiredAmount: String,
    val pfLoan: PfLoan,
    val processStatus: String,
    val rewrittenLoanId: RewrittenLoanId,
    val simulation: List<Simulation>,
    val simulationEndDate: SimulationEndDate,
    val simulationNextPaymentDueDate: SimulationNextPaymentDueDate,
    val simulationRunDate: SimulationRunDate,
    val simulationStartDate: SimulationStartDate,
    val sipClientId: SipClientId,
    val stateRewriteCredit: String,
    val toolProgram: String,
    val updatedChannelId: UpdatedChannelIdX,
    val updatedDate: UpdatedDateX,
    val updatedTransactionId: String,
    val updatedUserId: UpdatedUserIdX
)

data class Simulation(
    val _id: Id,
    val annualPercentageRate: String,
    val createdChannelId: CreatedChannelId,
    val createdDate: CreatedDate,
    val createdTransactionId: String,
    val createdUserId: CreatedUserId,
    val firstPaymentDate: FirstPaymentDate,
    val interestRate: String,
    val lastPaymentAmount: String,
    val lastPaymentDate: LastPaymentDate,
    val loanSimulationKey: String,
    val months: Int,
    val numberOfPayments: Int,
    val originationFee: String,
    val regularPaymentAmount: String,
    val rewriteLoanAmount: String,
    val rewrittenLoanId: RewrittenLoanId,
    val scenarioRequestId: ScenarioRequestId,
    val updatedChannelId: UpdatedChannelIdX,
    val updatedDate: UpdatedDateX,
    val updatedTransactionId: String,
    val updatedUserId: UpdatedUserIdX
)

data class Id(
    val `$numberLong`: String
)

data class CreatedChannelId(
    val `$numberLong`: String
)

data class CreatedDate(
    val `$date`: String
)

data class CreatedUserId(
    val `$numberLong`: String
)

data class DisbursementDate(
    val `$date`: String
)

data class ExpirationDate(
    val `$date`: String
)

data class LastPaycheckDate(
    val `$date`: String
)

data class NextPaymentDueDate(
    val `$date`: String
)

data class OriginalMaturityDate(
    val `$date`: String
)

data class PfLoan(
    val `$numberLong`: String
)

data class RewrittenLoanId(
    val `$numberLong`: String
)



data class SimulationEndDate(
    val `$date`: String
)

data class SimulationNextPaymentDueDate(
    val `$date`: String
)

data class SimulationRunDate(
    val `$date`: String
)

data class SimulationStartDate(
    val `$date`: String
)

data class SipClientId(
    val `$numberLong`: String
)

data class UpdatedChannelIdX(
    val `$numberLong`: String
)

data class UpdatedDateX(
    val `$date`: String
)

data class UpdatedUserIdX(
    val `$numberLong`: String
)

data class FirstPaymentDate(
    val `$date`: String
)

data class LastPaymentDate(
    val `$date`: String
)

data class ScenarioRequestId(
    val `$numberLong`: String
)