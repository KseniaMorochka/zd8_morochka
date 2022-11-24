package com.example.orgonizer1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
interface  UserActionListener{
    fun Sort()
    fun Deteils(film :Task)
    fun delete(film: Task)
    fun Search(namefilm : String)
}
private const val ID_DELETE =0
private const val ID_DETEILS =1
private  const val ID_SORT =2
private const val ID_SEARCH =3
private const val TAG="CrimeListFragment"

class TaskListFragment:Fragment() {

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }
    private var callbacks: Callbacks? = null
    private lateinit var taskRecyclerView: RecyclerView
    private var adapter: TaskAdapter? = TaskAdapter(emptyList())
    private val taskListViewModel: TaskListViewModle by lazy {
        ViewModelProviders.of(this).get(TaskListViewModle::class.java)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_task_list, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_task -> {
                val task = Task()
                taskListViewModel.addTask(task)
                callbacks?.onCrimeSelected(task.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)
        taskRecyclerView = view.findViewById(R.id.task_recycler_view) as RecyclerView
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.adapter = adapter
        return view
    }
    override fun onViewCreated(view: View,
                               savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListViewModel.taskListLiveData.observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer() { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got tasks${tasks.size}")
                    updateUI(tasks)
                }
            })
    }
    private fun updateUI(tasks: List<Task>) {
        adapter = TaskAdapter(tasks)
        taskRecyclerView.adapter = adapter
    }

    private inner class TaskHolder (view: View,   public  var  actionListener : UserActionListener)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var task: Task
        val titleTextView: TextView =
            itemView.findViewById(R.id.task_title)
        val dateTextView: TextView =
            itemView.findViewById(R.id.task_date)
        val morebutton : ImageView = itemView.findViewById(R.id.more)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(task: Task) {
            this.task = task
            titleTextView.text = this.task.title
            dateTextView.text = this.task.date.toString()
            itemView.tag = task
            morebutton.tag = task
        }
        override fun onClick(v: View) {
            val film = v.tag as Task
            showPopupMenu(v)
        }
        fun showPopupMenu(view:View) {
            val film2 = view.tag as Task
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menu.add(0, ID_DETEILS, Menu.NONE, "Details")
            popupMenu.menu.add(0, ID_DELETE, Menu.NONE, "Delete")
            /*popupMenu.menu.add(0, ID_SEARCH, Menu.NONE, "Search")
            popupMenu.menu.add(0, ID_SORT, Menu.NONE, "Sort")*/
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                if (item.itemId === ID_DETEILS) {
                    actionListener.Deteils(film2)
                }
                if (item.itemId === ID_SORT) {
                    actionListener.Sort()
                }
                if (item.itemId === ID_DELETE) {
                    actionListener.delete(film2)
                }
                if (item.itemId === ID_SEARCH) {
                    actionListener.Search("Котовасия")
                }
                false
            })
            popupMenu.show()
        }
    }
    private inner class TaskAdapter(var tasks: List<Task>)
        : RecyclerView.Adapter<TaskHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
            val view = layoutInflater.inflate(R.layout.list_item_task, parent, false)

            return TaskHolder(view,object : UserActionListener {
                override fun Deteils(film: Task) {
                    callbacks?.onCrimeSelected(film.id)
                }
                override fun Sort() {
                }
                override fun delete(film: Task) {
                    taskListViewModel.deleteFilm(film)
                }
                override fun Search(namefilm: String) {
                    tasks.forEach {
                            film ->film.title ==namefilm
                    }
                }
            }
            )
        }
        override fun getItemCount() = tasks.size
        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val film = tasks[position]
            holder.bind(film)
        }
    }
    companion object {
        fun newInstance(): TaskListFragment {
            return TaskListFragment()
        }
    }

}



