class AddDetailsToProjects < ActiveRecord::Migration[5.2]
  def change
    add_column :projects, :start_date, :date
    add_column :projects, :due_date, :date
    add_column :projects, :estimated_time, :integer
    add_column :projects, :priority, :string
    add_column :projects, :author_id, :integer
    add_column :projects, :assignee_id, :integer
  end
end
