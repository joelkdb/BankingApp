package fr.utbm.bindoomobile.data.repositories

import com.cioccarellia.ksprefs.KsPrefs
import fr.utbm.bindoomobile.data.app.PrefKeys
import fr.utbm.bindoomobile.data.datasource.local.dao.PersonInfoDao
import fr.utbm.bindoomobile.domain.models.feature_profile.CompactProfile
import fr.utbm.bindoomobile.domain.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProfileRepositoryMock(
    private val dispatcher: CoroutineDispatcher,
    private val personInfoDao: PersonInfoDao,
    private val prefs: KsPrefs
) : ProfileRepository {
    override suspend fun getCompactProfile(): CompactProfile = withContext(dispatcher) {
        delay(MOCK_DELAY)

        val personInfo = personInfoDao.getPersonInfos().first()

        return@withContext CompactProfile(
            id = prefs.pull(PrefKeys.USERNAME.name),
            firstName = personInfo.firstname,
            lastName = personInfo.lastname,
            nickName = buildNickname(personInfo.firstname, personInfo.lastname),
            email = "example@example.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey"
        )
    }

    companion object {
        private const val MOCK_DELAY = 300L
    }

    private fun buildNickname(firstname: String, lastname: String): String {
        return "@${firstname.lowercase()}${lastname.lowercase()}"
    }
}