package repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dto.Post

class PostRepositoryInMemoryImpl(application: Application) : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Освоение новой профессии — это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни: менять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки → http://netolo.gy/fPD",
            textView4 = "23 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            textView4 = "22 сентября в 10:14",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
            textView4 = "22 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
            textView4 = "21 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
            textView4 = "20 сентября в 10:14",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
            textView4 = "19 сентября в 14:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            textView4 = "19 сентября в 10:24",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
            textView4 = "18 сентября в 10:12",
            likedByMe = false
        ),
        Post(
            id = nextId++,
            textView2 = "Нетология. Университет интернет-профессий будущего",
            textView = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            textView4 = "21 мая в 18:36",
            likedByMe = false
        ),
    ).reversed()

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun save(post: Post) {
        if (post.id == 0L) {
            // TODO: remove hardcoded author & published
            posts = listOf(
                post.copy(
                    id = nextId++,
                    textView2 = "Me",
                    likedByMe = false,
                    textView4 = "now"
                )
            ) + posts
            data.value = posts
            return
        }

        posts = posts.map {
            if (it.id != post.id) it else it.copy(textView = post.textView)
        }
        data.value = posts
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
}