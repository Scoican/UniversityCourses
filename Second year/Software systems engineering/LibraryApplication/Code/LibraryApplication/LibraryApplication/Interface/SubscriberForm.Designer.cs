namespace LibraryApplication.Interface
{
    partial class SubscriberForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.BookGridView = new System.Windows.Forms.DataGridView();
            this.LogoutButton = new System.Windows.Forms.Button();
            this.BorrowButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.BookGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // BookGridView
            // 
            this.BookGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.BookGridView.Location = new System.Drawing.Point(12, 12);
            this.BookGridView.Name = "BookGridView";
            this.BookGridView.RowTemplate.Height = 24;
            this.BookGridView.Size = new System.Drawing.Size(508, 383);
            this.BookGridView.TabIndex = 0;
            this.BookGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.BookGridView_CellClick);
            // 
            // LogoutButton
            // 
            this.LogoutButton.Location = new System.Drawing.Point(337, 401);
            this.LogoutButton.Name = "LogoutButton";
            this.LogoutButton.Size = new System.Drawing.Size(183, 37);
            this.LogoutButton.TabIndex = 1;
            this.LogoutButton.Text = "Logut";
            this.LogoutButton.UseVisualStyleBackColor = true;
            this.LogoutButton.Click += new System.EventHandler(this.LogoutButton_Click);
            // 
            // BorrowButton
            // 
            this.BorrowButton.Location = new System.Drawing.Point(12, 402);
            this.BorrowButton.Name = "BorrowButton";
            this.BorrowButton.Size = new System.Drawing.Size(183, 36);
            this.BorrowButton.TabIndex = 2;
            this.BorrowButton.Text = "Borrow";
            this.BorrowButton.UseVisualStyleBackColor = true;
            this.BorrowButton.Click += new System.EventHandler(this.BorrowButton_Click);
            // 
            // SubscriberForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(532, 450);
            this.Controls.Add(this.BorrowButton);
            this.Controls.Add(this.LogoutButton);
            this.Controls.Add(this.BookGridView);
            this.Name = "SubscriberForm";
            this.Text = "SubscriberFrom";
            this.Load += new System.EventHandler(this.SubscriberFrom_Load);
            ((System.ComponentModel.ISupportInitialize)(this.BookGridView)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView BookGridView;
        private System.Windows.Forms.Button LogoutButton;
        private System.Windows.Forms.Button BorrowButton;
    }
}